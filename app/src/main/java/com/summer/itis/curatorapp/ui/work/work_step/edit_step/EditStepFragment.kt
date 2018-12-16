package com.summer.itis.curatorapp.ui.work.work_step.edit_step

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.summer.itis.curatorapp.R
import com.summer.itis.curatorapp.model.step.Step
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragment
import com.summer.itis.curatorapp.ui.base.navigation_base.NavigationView
import com.summer.itis.curatorapp.ui.work.work_step.add_step.DateListener
import com.summer.itis.curatorapp.utils.Const
import com.summer.itis.curatorapp.utils.Const.ID_KEY
import com.summer.itis.curatorapp.utils.Const.STEP_KEY
import com.summer.itis.curatorapp.utils.Const.gsonConverter
import com.summer.itis.curatorapp.utils.FormatterUtil
import com.summer.itis.curatorapp.widget.DatePickerFragment
import kotlinx.android.synthetic.main.fragment_add_step.*
import kotlinx.android.synthetic.main.fragment_step.*
import kotlinx.android.synthetic.main.toolbar_back_done.*

class EditStepFragment : BaseFragment<EditStepPresenter>(), EditStepView, View.OnClickListener {

    private lateinit var step: Step
    private lateinit var workId: String

    override lateinit var mainListener: NavigationView

    @InjectPresenter
    lateinit var presenter: EditStepPresenter

    lateinit var dialogStart: DatePickerFragment
    lateinit var dialogFinish: DatePickerFragment

    companion object {

        fun newInstance(args: Bundle, mainListener: NavigationView): Fragment {
            val fragment = EditStepFragment()
            fragment.arguments = args
            fragment.mainListener = mainListener
            return fragment
        }

        fun newInstance(mainListener: NavigationView): Fragment {
            val fragment = EditStepFragment()
            fragment.mainListener = mainListener
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mainListener.hideBottomNavigation()
        mainListener.changeWindowsSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        val view = inflater.inflate(R.layout.fragment_add_step, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.getString(ID_KEY)?.let { workId = it }
        arguments?.getString(STEP_KEY)?.let { step = gsonConverter.fromJson(it, Step::class.java) }
        initViews()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initViews() {
        dialogStart = DatePickerFragment()
        dialogFinish = DatePickerFragment()
        setToolbarData()
        setListeners()
        setStepData()
    }

    private fun setToolbarData() {
        mainListener.setToolbar(toolbar_back_done)
    }

    private fun setListeners() {
        btn_ok.setOnClickListener(this)
        btn_back.setOnClickListener(this)
        et_date_start.setOnClickListener(this)
        et_date_finish.setOnClickListener(this)
        dialogStart.callback = object : DateListener {
            override fun setDate(dateStr: String) {
                et_date_start.setText(dateStr)
            }
        }
        dialogFinish.callback = object : DateListener {
            override fun setDate(dateStr: String) {
                et_date_finish.setText(dateStr)
            }
        }
    }

    private fun setStepData() {
        et_title.setText(step.title)
        et_date_start.setText(FormatterUtil.getStringFromDate(step.dateStart))
        et_date_finish.setText(FormatterUtil.getStringFromDate(step.dateFinish))
        et_description.setText(step.description)
        et_links.setText(step.links)
        dialogStart.setDate(step.dateStart)
        dialogFinish.setDate(step.dateFinish)
    }

    override fun onClick(v: View) {
        when (v.id) {

            R.id.btn_ok -> {

                step.title = et_title.text.toString()
                step.description = et_description.text.toString()
                step.links = et_links.text.toString()
                step.dateStart = dialogStart.getDate()
                step.dateFinish = dialogFinish.getDate()
                if(validateData()) {
                    context?.let { presenter.updateStep(workId, step) }
                } else {
                    mainListener.showSnackBar(getString(R.string.invalid_fields))
                }
            }

            R.id.btn_back -> backFragment()

            R.id.et_date_start ->  dialogStart.show(activity?.supportFragmentManager, "dateStart")

            R.id.et_date_finish ->  dialogFinish.show(activity?.supportFragmentManager, "dateFinish")

        }
    }

    override fun backAfterEdit(intent: Intent) {
        targetFragment?.onActivityResult(Const.EDIT_STEP, Activity.RESULT_OK, intent)
        mainListener.hideFragment()
    }

    private fun validateData(): Boolean{
        if(step.title.equals("") || step.description.equals("") || step.dateStart.equals(step.dateFinish) || step.links.equals("")) {
            return false
        } else {
            return true
        }
    }
}
