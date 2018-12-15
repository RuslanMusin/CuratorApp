package com.summer.itis.curatorapp.ui.work.work_step.step

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.ms.square.android.expandabletextview.ExpandableTextView
import com.summer.itis.curatorapp.R
import com.summer.itis.curatorapp.model.step.Step
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragment
import com.summer.itis.curatorapp.ui.base.navigation_base.NavigationBaseActivity.Companion.SHOW_WORKS
import com.summer.itis.curatorapp.ui.base.navigation_base.NavigationView
import com.summer.itis.curatorapp.ui.work.work_step.edit_step.EditStepFragment
import com.summer.itis.curatorapp.utils.Const.EDIT_STEP
import com.summer.itis.curatorapp.utils.Const.ID_KEY
import com.summer.itis.curatorapp.utils.Const.STEP_KEY
import com.summer.itis.curatorapp.utils.Const.gsonConverter
import com.summer.itis.curatorapp.utils.FormatterUtil
import kotlinx.android.synthetic.main.fragment_step.*
import kotlinx.android.synthetic.main.toolbar_edit_back.*

class StepFragment: BaseFragment<StepPresenter>(), StepView, View.OnClickListener {

    override lateinit var mainListener: NavigationView
    lateinit var step: Step
    lateinit var workId: String

    @InjectPresenter
    lateinit var presenter: StepPresenter

    companion object {

        const val TAG_CURATOR = "TAG_CURATOR"

        const val EDIT_CURATOR = 1

        fun newInstance(args: Bundle, navigationView: NavigationView): Fragment {
            val fragment = StepFragment()
            fragment.arguments = args
            fragment.mainListener = navigationView
            return fragment
        }

        fun newInstance(navigationView: NavigationView): Fragment {
            val fragment = StepFragment()
            fragment.mainListener = navigationView
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_step, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            step = gsonConverter.fromJson(it.getString(STEP_KEY), Step::class.java)
            workId = it.getString(ID_KEY)
        }
    }

    fun initViews() {
        setToolbarData()
        setListeners()
        setData()
    }

    private fun setToolbarData() {
        mainListener.setToolbar(toolbar_edit_back)
        mainListener.setToolbarTitle(getString(R.string.work))
    }

    private fun setListeners() {
        btn_back.setOnClickListener(this)
        btn_edit.setOnClickListener(this)
    }

    private fun setData() {
        tv_title.text = step.title
        tv_date_start.text = FormatterUtil.getStringFromDate(step.dateStart)
        tv_date_finish.text = FormatterUtil.getStringFromDate(step.dateFinish)
        val tvDesc: ExpandableTextView = li_desc.findViewById(R.id.expand_text_view)
        val tvLinks: ExpandableTextView = li_links.findViewById(R.id.expand_text_view)
        tvDesc.text = step.description
        tvLinks.text = step.links
    }

    override fun onClick(v: View) {
        when (v.id) {

            R.id.btn_back -> backFragment()

            R.id.btn_edit -> editStep()
        }
    }

    private fun editStep() {
        val args = Bundle()
        args.putString(STEP_KEY, gsonConverter.toJson(step))
        args.putString(ID_KEY, workId)
        val fragment = EditStepFragment.newInstance(args, mainListener)
        fragment.setTargetFragment(this, EDIT_STEP)
        mainListener.showFragment(SHOW_WORKS,this, fragment)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when(requestCode) {

                EDIT_STEP -> {
                    val json = data?.getStringExtra(STEP_KEY)
                    json?.let {
                        step = gsonConverter.fromJson(json, Step::class.java)
                        tv_title.text = step.title
                        tv_date_start.text = FormatterUtil.getStringFromDate(step.dateStart)
                        tv_date_finish.text = FormatterUtil.getStringFromDate(step.dateFinish)
                        val tvDesc: ExpandableTextView = li_desc.findViewById(R.id.expand_text_view)
                        val tvLinks: ExpandableTextView = li_links.findViewById(R.id.expand_text_view)
                        tvDesc.text = step.description
                        tvLinks.text = step.links

                        mainListener.showSnackBar(getString(R.string.changes_updated))

                    }
                }

            }
        }
    }
}
