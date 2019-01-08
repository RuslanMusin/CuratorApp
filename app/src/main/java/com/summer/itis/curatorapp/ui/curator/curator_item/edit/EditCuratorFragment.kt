package com.summer.itis.curatorapp.ui.curator.curator_item.edit

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.summer.itis.curatorapp.R
import com.summer.itis.curatorapp.model.user.Curator
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragment
import com.summer.itis.curatorapp.ui.base.navigation_base.NavigationView
import com.summer.itis.curatorapp.utils.AppHelper
import com.summer.itis.curatorapp.utils.Const.CURATOR_KEY
import com.summer.itis.curatorapp.utils.Const.EDIT_CURATOR
import com.summer.itis.curatorapp.utils.Const.gsonConverter
import kotlinx.android.synthetic.main.fragment_change_profile.*
import kotlinx.android.synthetic.main.toolbar_back_done.*

class EditCuratorFragment : BaseFragment<EditCuratorPresenter>(), EditCuratorView, View.OnClickListener {

    lateinit var curator: Curator
    override lateinit var mainListener: NavigationView

    @InjectPresenter
    lateinit var presenter: EditCuratorPresenter

    companion object {

        fun newInstance(args: Bundle, navigationView: NavigationView): Fragment {
            val fragment = EditCuratorFragment()
            fragment.arguments = args
            fragment.mainListener = navigationView
            return fragment
        }

        fun newInstance(navigationView: NavigationView): Fragment {
            val fragment = EditCuratorFragment()
            fragment.mainListener = navigationView
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            val curatorJson = it.getString(CURATOR_KEY)
            curatorJson?.let {
                curator = gsonConverter.fromJson(curatorJson, Curator::class.java)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_change_profile, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews()
        super.onViewCreated(view, savedInstanceState)
    }

    fun initViews() {
        setToolbarData()
        setListeners()
        setUserData()
    }

    private fun setToolbarData() {
        mainListener.setToolbar(toolbar_back_done)
        toolbar_title.text = getString(R.string.edit_profile)
    }

    private fun setListeners() {
        btn_ok.setOnClickListener(this)
        btn_back.setOnClickListener(this)
    }

    private fun setUserData() {
        et_name.setText(curator.name)
        et_lastname.setText(curator.lastname)
        et_patronymic.setText(curator.patronymic)

    }

    override fun onClick(v: View) {
        when (v.id) {

            R.id.btn_ok -> changeData()

            R.id.btn_back -> {
                backFragment()
            }
        }
    }

    private fun changeData() {
        curator.name = et_name.text.toString()
        curator.lastname = et_lastname.text.toString()
        curator.patronymic = et_patronymic.text.toString()
        presenter.updateCurator(curator)
    }

    override fun returnAfterEdit() {
        val intent = Intent()
        intent.putExtra(CURATOR_KEY, gsonConverter.toJson(curator))
        targetFragment?.onActivityResult(EDIT_CURATOR, RESULT_OK, intent)
        mainListener.hideFragment()
    }
}
