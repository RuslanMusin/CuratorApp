package com.summer.itis.curatorapp.ui.work.work_step.step

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.ms.square.android.expandabletextview.ExpandableTextView
import com.summer.itis.curatorapp.R
import com.summer.itis.curatorapp.model.step.Step
import com.summer.itis.curatorapp.ui.base.navigation_base.NavigationView
import com.summer.itis.curatorapp.ui.comment.CommentFragment
import com.summer.itis.curatorapp.ui.work.work_step.edit_step.EditStepFragment
import com.summer.itis.curatorapp.ui.work.work_step.material.list.MaterialListFragment
import com.summer.itis.curatorapp.utils.AppHelper
import com.summer.itis.curatorapp.utils.Const
import com.summer.itis.curatorapp.utils.Const.CURATOR_KEY
import com.summer.itis.curatorapp.utils.Const.EDIT_STEP
import com.summer.itis.curatorapp.utils.Const.ID_KEY
import com.summer.itis.curatorapp.utils.Const.STEP_KEY
import com.summer.itis.curatorapp.utils.Const.WORK_KEY
import com.summer.itis.curatorapp.utils.Const.gsonConverter
import com.summer.itis.curatorapp.utils.FormatterUtil
import kotlinx.android.synthetic.main.layout_add_comment.*
import kotlinx.android.synthetic.main.layout_step.*
import kotlinx.android.synthetic.main.toolbar_edit_back.*

class StepFragment: CommentFragment<StepPresenter>(), StepView, View.OnClickListener {

    override var map: HashMap<String, String> = HashMap()
    override var type: String = Const.STEP_TYPE

    override lateinit var mainListener: NavigationView
    lateinit var step: Step
    lateinit var workId: String

    @InjectPresenter
    override lateinit var presenter: StepPresenter

    companion object {

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
        mainListener.hideBottomNavigation()
        mainListener.changeWindowsSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        arguments?.let {
            workId = it.getString(WORK_KEY)
            val stepId = it.getString(STEP_KEY)
            map[CURATOR_KEY] = AppHelper.currentCurator.id
            map[WORK_KEY] = workId
            map[STEP_KEY] = stepId
            presenter.loadStep(workId, stepId)
            presenter.loadComments(map, type)
        }
    }

    override fun showStep(step: Step) {
        this.step = step
        tv_title.text = step.title
        tv_date_start.text = FormatterUtil.getStringFromDate(step.dateStart)
        tv_date_finish.text = FormatterUtil.getStringFromDate(step.dateFinish)
        val tvDesc: ExpandableTextView = li_desc.findViewById(R.id.expand_text_view)
//        val tvLinks: ExpandableTextView = li_links.findViewById(R.id.expand_text_view)
        tvDesc.text = step.description
//        tvLinks.content = step.links
    }

    fun initViews() {
        setToolbarData()
        setListeners()
    }

    private fun setToolbarData() {
        mainListener.setToolbar(toolbar_edit_back)
        toolbar_title.text = getString(R.string.step)
    }

    private fun setListeners() {
        btn_back.setOnClickListener(this)
        btn_edit.setOnClickListener(this)
        li_links.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {

            R.id.btn_back -> backFragment()

            R.id.btn_edit -> editStep()

            R.id.li_links -> showMaterials()
        }
    }

    private fun showMaterials() {
        val args = Bundle()
        args.putString(WORK_KEY, workId)
        args.putString(Const.STEP_KEY, step.id)
        val fragment = MaterialListFragment.newInstance(args, mainListener)
        mainListener.pushFragments(fragment, true)
    }

    private fun editStep() {
        val args = Bundle()
        args.putString(STEP_KEY, gsonConverter.toJson(step))
        args.putString(ID_KEY, workId)
        val fragment = EditStepFragment.newInstance(args, mainListener)
        fragment.setTargetFragment(this, EDIT_STEP)
        mainListener.showFragment(this, fragment)
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
//                        val tvLinks: ExpandableTextView = li_links.findViewById(R.id.expand_text_view)
                        tvDesc.text = step.description
//                        tvLinks.content = step.links

                        mainListener.showSnackBar(getString(R.string.changes_updated))

                    }
                }

            }
        }
    }

    override fun clearAfterSendComment() {
        scrollView.fullScroll(View.FOCUS_DOWN)
        et_comment.setText(null)
        view?.getRootView()?.let {
            AppHelper.hideKeyboardFrom(this.activity as Context, it)
            Log.d(Const.TAG_LOG, "hide keyboard")
        }
        et_comment.clearFocus()    }
}
