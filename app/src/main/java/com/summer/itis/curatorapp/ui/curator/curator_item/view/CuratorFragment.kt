package com.summer.itis.curatorapp.ui.curator.curator_item.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.*
import com.arellomobile.mvp.presenter.InjectPresenter
import com.summer.itis.curatorapp.R
import com.summer.itis.curatorapp.model.user.Curator
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragment
import com.summer.itis.curatorapp.ui.base.navigation_base.NavigationBaseActivity.Companion.SHOW_PROFILE
import com.summer.itis.curatorapp.ui.base.navigation_base.NavigationBaseActivity.Companion.TAB_PROFILE
import com.summer.itis.curatorapp.ui.base.navigation_base.NavigationView
import com.summer.itis.curatorapp.ui.curator.curator_item.description.view.DescriptionFragment
import com.summer.itis.curatorapp.ui.curator.curator_item.edit.EditCuratorFragment
import com.summer.itis.curatorapp.ui.login.LoginActivity
import com.summer.itis.curatorapp.ui.skill.skill_list.view.SkillListFragment
import com.summer.itis.curatorapp.ui.work.one_work_list.OneWorkListFragment
import com.summer.itis.curatorapp.utils.AppHelper
import com.summer.itis.curatorapp.utils.Const.CURATOR_KEY
import com.summer.itis.curatorapp.utils.Const.CURATOR_TYPE
import com.summer.itis.curatorapp.utils.Const.DESC_KEY
import com.summer.itis.curatorapp.utils.Const.ID_KEY
import com.summer.itis.curatorapp.utils.Const.MAX_LENGTH
import com.summer.itis.curatorapp.utils.Const.OWNER_TYPE
import com.summer.itis.curatorapp.utils.Const.TAB_NAME
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

        const val TAG_CURATOR = "TAG_CURATOR"

        const val EDIT_CURATOR = 1

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
        Log.d(TAG_CURATOR, "type = " + type)
        this.type = type
        this.curator = curator
        findViews()
        setToolbarData()
        setListeners()
    }

    private fun setToolbarData() {
        if(type.equals(OWNER_TYPE)) {
            mainListener.setToolbar(toolbar)
        } else {
           /* setSupportActionBar(tb_profile)
            setBackArrow(tb_profile)*/
        }
        mainListener.setToolbarTitle(getString(R.string.menu_profile))
//        toolbar_edit.title = "${step.name} ${step.lastname} ${step.patronymic}"

    }

    private fun setListeners() {
      /*  btn_change.setOnClickListener(this)
        btn_give_theme.setOnClickListener(this)*/
//        btn_edit.setOnClickListener(this)
        li_skills.setOnClickListener(this)
//        li_works.setOnClickListener(this)
        li_logout.setOnClickListener(this)
        li_desc.setOnClickListener(this)
    }

    private fun findViews() {
        setUserData()
        li_works_vertical.visibility = View.GONE
    }

    private fun setUserData() {
        tv_username.text = "${curator.name} ${curator.lastname} ${curator.patronymic}"
        tv_desc.text = AppHelper.cutLongDescription(curator.description, MAX_LENGTH)
        AppHelper.setUserPhoto(iv_user_photo, curator, activity as Activity)
//        setUserBtnText()
    }

    /*   private fun setUserBtnText() {
           when (type) {
               ADD_FRIEND -> btn_add_friend.setText(R.string.add_friend)

               ADD_REQUEST -> btn_add_friend.setText(R.string.add_friend)

               REMOVE_FRIEND -> btn_add_friend.setText(R.string.remove_friend)

               REMOVE_REQUEST -> btn_add_friend.setText(R.string.remove_request)

               OWNER_TYPE -> {
                   btn_add_friend.visibility = View.GONE
                   btn_play_game.visibility = View.GONE
               }
           }
       }*/

    override fun onClick(v: View) {
        when (v.id) {

         /*   R.id.btn_change -> changeData()

            R.id.btn_give_theme -> giveTheme()*/

//            R.id.btn_edit -> editProfile()

            R.id.li_skills -> showSkills()

//            R.id.li_works -> showWorks()

            R.id.li_logout -> presenter.logout()

            R.id.li_desc -> showDesc()
        }
    }

    private fun showDesc() {
        val args = Bundle()
        args.putString(DESC_KEY, curator.description)
        args.putString(TYPE, CURATOR_TYPE)
        args.putString(USER_ID, curator.id)
        val fragment = DescriptionFragment.newInstance(args, mainListener)
        mainListener.pushFragments(TAB_PROFILE, fragment, true)
    }

    private fun editProfile() {
        val fragment = EditCuratorFragment.newInstance(mainListener)
        fragment.setTargetFragment(this, EDIT_CURATOR)
        mainListener.showFragment(SHOW_PROFILE, this, fragment)

//        mainListener.pushFragments(TAB_PROFILE, fragment, true)
        //        mainListener.loadFragment(fragment)

    }

    override fun logout() {
        LoginActivity.start(requireActivity())
    }

    private fun giveTheme() {
        /*  changePlayButton(false)
          presenter.checkGameCondition(step)*/
    }

    private fun showSkills() {
        val args = argUser(curator)
        args.putString(ID_KEY, curator.id)
        val fragment = SkillListFragment.newInstance(args, mainListener)
        mainListener.pushFragments(TAB_PROFILE, fragment, true)
    }

    private fun showWorks() {
        val args = argUser(curator)
        args.putString(TAB_NAME, TAB_PROFILE)
        val fragment = OneWorkListFragment.newInstance(args, mainListener)
        mainListener.pushFragments(TAB_PROFILE, fragment, true)
//        mainListener.loadFragment(WorkListFragment.newInstance(argUser(step), mainListener))
        /* val intent: Intent = Intent(this,OneCardListActivity::class.java)
         intent.putExtra(USER_ID,step.id)
         OneCardListActivity.start(this,intent)*/
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
        tv_username.text = "${curator.name} ${curator.lastname} ${curator.patronymic}"
    }
}
