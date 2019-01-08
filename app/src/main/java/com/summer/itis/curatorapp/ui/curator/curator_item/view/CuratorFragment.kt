package com.summer.itis.curatorapp.ui.curator.curator_item.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import com.arellomobile.mvp.presenter.InjectPresenter
import com.summer.itis.curatorapp.R
import com.summer.itis.curatorapp.model.user.Curator
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragment
import com.summer.itis.curatorapp.ui.base.navigation_base.NavigationView
import com.summer.itis.curatorapp.ui.curator.curator_item.description.view.CuratorDescFragment
import com.summer.itis.curatorapp.ui.curator.curator_item.edit.EditCuratorFragment
import com.summer.itis.curatorapp.ui.login.LoginActivity
import com.summer.itis.curatorapp.ui.skill.skill_list.view.SkillListFragment
import com.summer.itis.curatorapp.utils.AppHelper
import com.summer.itis.curatorapp.utils.Const
import com.summer.itis.curatorapp.utils.Const.CURATOR_KEY
import com.summer.itis.curatorapp.utils.Const.CURATOR_TYPE
import com.summer.itis.curatorapp.utils.Const.DESC_KEY
import com.summer.itis.curatorapp.utils.Const.EDIT_CURATOR
import com.summer.itis.curatorapp.utils.Const.ID_KEY
import com.summer.itis.curatorapp.utils.Const.MAX_LENGTH
import com.summer.itis.curatorapp.utils.Const.OWNER_TYPE
import com.summer.itis.curatorapp.utils.Const.TYPE
import com.summer.itis.curatorapp.utils.Const.USER_ID
import com.summer.itis.curatorapp.utils.Const.gsonConverter
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.layout_personal.*

class CuratorFragment : BaseFragment<CuratorPresenter>(), CuratorView, View.OnClickListener {

    lateinit var curator: Curator
    var type: String = OWNER_TYPE
    override lateinit var mainListener: NavigationView

    @InjectPresenter
    lateinit var presenter: CuratorPresenter

    companion object {

        fun newInstance(args: Bundle, navigationView: NavigationView): Fragment {
            val fragment = CuratorFragment()
            fragment.arguments = args
            fragment.mainListener = navigationView
            return fragment
        }

        fun newInstance(navigationView: NavigationView): Fragment {
            val fragment = CuratorFragment()
            fragment.mainListener = navigationView
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        arguments?.let {
            val curatorId = it.getString(ID_KEY)
            curatorId?.let {
                presenter.findCurator(curatorId)
            }
        }
    }

    override fun initViews(curator: Curator, type: String) {
        this.type = type
        this.curator = curator
        findViews()
        setToolbarData()
        setListeners()
    }

    private fun setToolbarData() {
        if(type.equals(OWNER_TYPE)) {
            mainListener.setToolbar(toolbar)
        }
        mainListener.setToolbarTitle(getString(R.string.menu_profile))
    }

    private fun setListeners() {
        li_skills.setOnClickListener(this)
        li_logout.setOnClickListener(this)
        li_desc.setOnClickListener(this)
    }

    private fun findViews() {
        setUserData()
        li_works_vertical.visibility = View.GONE
    }

    private fun setUserData() {
        tv_username.text = curator.getFullName()
        tv_desc.text = AppHelper.cutLongDescription(curator.description, MAX_LENGTH)
    }

    override fun onClick(v: View) {
        when (v.id) {

            R.id.li_skills -> showSkills()

            R.id.li_logout -> presenter.logout()

            R.id.li_desc -> showDesc()
        }
    }

    private fun showDesc() {
        val args = Bundle()
        args.putString(DESC_KEY, curator.description)
        args.putString(TYPE, CURATOR_TYPE)
        args.putString(USER_ID, curator.id)
        val fragment = CuratorDescFragment.newInstance(args, mainListener)
        mainListener.pushFragments(fragment, true)
    }

    private fun editProfile() {
        val args = Bundle()
        args.putString(CURATOR_KEY, gsonConverter.toJson(curator))
        val fragment = EditCuratorFragment.newInstance(args, mainListener)
        fragment.setTargetFragment(this, EDIT_CURATOR)
        mainListener.showFragment(this, fragment)
    }

    override fun logout() {
        activity?.let { LoginActivity.start(it) }
    }

    private fun showSkills() {
        val args: Bundle = Bundle()
        val userJson = Const.gsonConverter.toJson(curator)
        args.putString(Const.USER_KEY, userJson)
        args.putString(Const.PERSON_TYPE, CURATOR_TYPE)
        args.putString(ID_KEY, curator.id)
        val fragment = SkillListFragment.newInstance(args, mainListener)
        mainListener.pushFragments(fragment, true)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.edit_menu, menu)
        menu?.findItem(R.id.action_edit)?.setOnMenuItemClickListener {
            editProfile()
            true
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when(requestCode) {

                EDIT_CURATOR -> data?.let { changeData(it) }
            }
        }
    }

    private fun changeData(data: Intent) {
        curator = gsonConverter.fromJson(data.getStringExtra(CURATOR_KEY), Curator::class.java)
        tv_username.text = curator.getFullName()
    }
}
