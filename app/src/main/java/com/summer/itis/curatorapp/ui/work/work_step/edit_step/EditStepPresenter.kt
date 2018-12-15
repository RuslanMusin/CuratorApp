package com.summer.itis.curatorapp.ui.work.work_step.edit_step

import android.content.Context
import android.content.Intent
import com.arellomobile.mvp.InjectViewState
import com.summer.itis.curatorapp.model.step.Step
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragPresenter
import com.summer.itis.curatorapp.utils.AppHelper
import com.summer.itis.curatorapp.utils.Const
import io.reactivex.disposables.CompositeDisposable


@InjectViewState
class EditStepPresenter(): BaseFragPresenter<EditStepView>() {

    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun editStep(step: Step, workId: String, context: Context) {
        val works = AppHelper.currentCurator.works
        for(work in works) {
            if(work.id.equals(workId)) {
                val steps = work.steps
                for(i in steps.indices) {
                    if(steps[i].id.equals(step.id)) {
                        steps[i] = step
                    }
                }
            }
        }
        AppHelper.saveCurrentState(AppHelper.currentCurator, context)
        val intent = Intent()
        intent.putExtra(Const.STEP_KEY, Const.gsonConverter.toJson(step))
        viewState.backAfterEdit(intent)
    }
}