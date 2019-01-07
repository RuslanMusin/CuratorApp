package com.summer.itis.curatorapp.ui.work.work_step.add_step

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.summer.itis.curatorapp.model.step.Step
import com.summer.itis.curatorapp.repository.RepositoryProvider
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragPresenter
import com.summer.itis.curatorapp.utils.AppHelper
import com.summer.itis.curatorapp.utils.Const
import io.reactivex.disposables.CompositeDisposable

@InjectViewState
class AddStepPresenter(): BaseFragPresenter<AddStepView>() {

    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun addStep(workId: String, step: Step) {
        step.setApiFields()
        val disposable = RepositoryProvider.workStepRepository
            .postCuratorWorkStep(AppHelper.currentCurator.id, workId, step)
            .subscribe { res ->
                interceptResponse(res) { viewState.backAfterAdd() }
        }
        compositeDisposable.add(disposable)
    }
}