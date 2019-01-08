package com.summer.itis.curatorapp.ui.description

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.summer.itis.curatorapp.R
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragment
import com.summer.itis.curatorapp.ui.base.navigation_base.NavigationView
import com.summer.itis.curatorapp.utils.Const
import kotlinx.android.synthetic.main.fragment_description.*
import kotlinx.android.synthetic.main.toolbar_edit_back.*
import kotlinx.android.synthetic.main.toolbar_edit_back.view.*

class DescriptionFragment : BaseFragment<DescriptionPresenter>(), DescriptionView, View.OnClickListener {

    lateinit var type: String
    var isOwner: Boolean = false
    lateinit var description: String
    var id: String? = null
    lateinit override var mainListener: NavigationView

    @InjectPresenter
    lateinit var presenter: DescriptionPresenter

    companion object {

        fun newInstance(args: Bundle, navigationView: NavigationView): Fragment {
            val fragment = DescriptionFragment()
            fragment.arguments = args
            fragment.mainListener = navigationView
            return fragment
        }

        fun newInstance(navigationView: NavigationView): Fragment {
            val fragment = DescriptionFragment()
            fragment.mainListener = navigationView
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            description = it.getString(Const.DESC_KEY)
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_description, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    fun initViews() {
        setToolbarData()
        setListeners()
        setData()
    }

    private fun setToolbarData() {
        mainListener.setToolbar(toolbar_edit_back)
        btn_edit.visibility = View.GONE
        toolbar_edit_back.toolbar_title.text = getString(R.string.desc)

    }

    private fun setListeners() {
        btn_back.setOnClickListener(this)
    }

    private fun setData() {
        tv_desc_name.text = description
    }


    override fun onClick(v: View) {
        when (v.id) {

            R.id.btn_back -> backFragment()

        }
    }

}