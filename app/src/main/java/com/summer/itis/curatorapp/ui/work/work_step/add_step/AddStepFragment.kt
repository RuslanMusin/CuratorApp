package com.summer.itis.curatorapp.ui.work.work_step.add_step

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.summer.itis.curatorapp.R
import com.summer.itis.curatorapp.model.step.Step
import com.summer.itis.curatorapp.model.theme.Status
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragment
import com.summer.itis.curatorapp.ui.base.navigation_base.NavigationView
import com.summer.itis.curatorapp.utils.Const.ID_KEY
import com.summer.itis.curatorapp.utils.Const.IN_PROCESS
import com.summer.itis.curatorapp.widget.DatePickerFragment
import kotlinx.android.synthetic.main.fragment_add_step.*
import kotlinx.android.synthetic.main.toolbar_back_done.*
import java.util.*

class AddStepFragment : BaseFragment<AddStepPresenter>(), AddStepView, View.OnClickListener {

    private lateinit var step: Step
    private lateinit var workId: String

    override lateinit var mainListener: NavigationView

    @InjectPresenter
    lateinit var presenter: AddStepPresenter

    lateinit var dialogStart: DatePickerFragment
    lateinit var dialogFinish: DatePickerFragment

    companion object {

        fun newInstance(args: Bundle, mainListener: NavigationView): Fragment {
            val fragment = AddStepFragment()
            fragment.arguments = args
            fragment.mainListener = mainListener
            return fragment
        }

        fun newInstance(mainListener: NavigationView): Fragment {
            val fragment = AddStepFragment()
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
        step = Step()
        initViews()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initViews() {
        setTime()
        setToolbarData()
        setListeners()
    }

    private fun setTime() {
        dialogStart = DatePickerFragment()
        dialogFinish = DatePickerFragment()
        dialogStart.setDate(Date())
        dialogFinish.setDate(Date())
    }

    private fun setToolbarData() {
        mainListener.setToolbar(toolbar_back_done)
        toolbar_title.text = getString(R.string.add_step)
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

    override fun onClick(v: View) {
        when (v.id) {

            R.id.btn_ok -> {

                step.id = "${Random().nextInt(24000)}"
                step.title = et_title.text.toString()
                step.description = et_description.text.toString()
                step.dateStart = dialogStart.getDate()
                step.dateFinish = dialogFinish.getDate()
                step.status = Status(IN_PROCESS, getString(R.string.in_process))
                if(validateData()) {
                    context?.let { presenter.addStep(workId, step) }
                } else {
                    mainListener.showSnackBar(getString(R.string.invalid_fields))
                }
            }

            R.id.btn_back -> backFragment()

            R.id.et_date_start ->  dialogStart.show(activity?.supportFragmentManager, "dateStart")

            R.id.et_date_finish ->  dialogFinish.show(activity?.supportFragmentManager, "dateFinish")

        }
    }

    override fun backAfterAdd() {
        backFragment()
    }

    private fun validateData(): Boolean{
        if(step.title.equals("") || step.description.equals("")
            || step.dateStart.equals(step.dateFinish) || step.links.equals("")) {
            return false
        } else {
            return true
        }
    }
}
