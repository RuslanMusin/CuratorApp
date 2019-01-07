package com.summer.itis.curatorapp.ui.work.progress_card

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.summer.itis.curatorapp.model.step.Step
import com.summer.itis.curatorapp.repository.RepositoryProvider
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragPresenter
import com.summer.itis.curatorapp.utils.Const
import io.reactivex.disposables.CompositeDisposable

@InjectViewState
class StepListPresenter(): BaseFragPresenter<StepListView>() {

    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun loadSteps(workId: String) {
        val disposable = RepositoryProvider.workStepRepository
            .findAll(workId)
            .subscribe { res ->
                interceptResponse(res) {
                    viewState.showSteps(it.sortedWith(compareBy (Step::dateFinish)))
                }
        }
        compositeDisposable.add(disposable)
    }
}