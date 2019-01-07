package com.summer.itis.curatorapp.ui.base.connection

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.summer.itis.curatorapp.R
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragment
import com.summer.itis.curatorapp.ui.base.navigation_base.NavigationView
import kotlinx.android.synthetic.main.fragment_connectivity.*
import kotlinx.android.synthetic.main.toolbar_back.*

class ConnectionFragment: BaseFragment<ConnectionPresenter>(), ConnectionView, View.OnClickListener {

    override lateinit var mainListener: NavigationView

    @InjectPresenter
    lateinit var presenter: ConnectionPresenter

    companion object {


        fun newInstance(args: Bundle, navigationView: NavigationView): Fragment {
            val fragment = ConnectionFragment()
            fragment.arguments = args
            fragment.mainListener = navigationView
            return fragment
        }

        fun newInstance(navigationView: NavigationView): Fragment {
            val fragment = ConnectionFragment()
            fragment.mainListener = navigationView
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_connectivity, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    fun initViews() {
        setToolbarData()
        setListeners()
    }

    private fun setToolbarData() {
        mainListener.setToolbar(toolbar_back)
        toolbar_title.text = getString(R.string.server_reset)
    }

    private fun setListeners() {
        btn_back.setOnClickListener(this)
        iv_reconnect.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {

            R.id.btn_back -> backFragment()

            R.id.iv_reconnect -> reconnect()
        }
    }

    private fun reconnect() {
       /* val args = Bundle()
        args.putString(Const.USER_ID, work.theme.student?.id)
        args.putString(Const.TAB_NAME, NavigationBaseActivity.TAB_WORKS)
        val fragment = StudentFragment.newInstance(args, mainListener)
        mainListener.pushFragments(NavigationBaseActivity.TAB_WORKS, fragment, true)*/
        mainListener.hideFragment()
    }

}
