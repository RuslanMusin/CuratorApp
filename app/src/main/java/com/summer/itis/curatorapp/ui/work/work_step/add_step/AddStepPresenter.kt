package com.summer.itis.curatorapp.ui.work.work_step.add_step

import android.content.Context
import com.arellomobile.mvp.InjectViewState
import com.summer.itis.curatorapp.model.step.Step
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragPresenter
import com.summer.itis.curatorapp.utils.AppHelper
import io.reactivex.disposables.CompositeDisposable

@InjectViewState
class AddStepPresenter(): BaseFragPresenter<AddStepView>() {

    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun addStep(step: Step, workId: String, context: Context) {
        val works = AppHelper.currentCurator.works
        for(work in works) {
            if(work.id.equals(workId)) {
                work.steps.add(step)
            }
        }
        AppHelper.saveCurrentState(AppHelper.currentCurator, context)
        viewState.backAfterAdd()
    }
}