package com.summer.itis.curatorapp.ui.work.work_step.step

import com.summer.itis.curatorapp.model.step.Step
import com.summer.itis.curatorapp.ui.comment.CommentView

interface StepView: CommentView {

    fun showStep(step: Step)

}