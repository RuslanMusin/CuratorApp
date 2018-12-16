package com.summer.itis.curatorapp.ui.work.work_step.step

import com.summer.itis.curatorapp.model.step.Step
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragView

interface StepView: BaseFragView {

    fun showStep(step: Step)

}