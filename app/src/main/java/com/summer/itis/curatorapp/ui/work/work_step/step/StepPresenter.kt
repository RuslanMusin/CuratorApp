package com.summer.itis.curatorapp.ui.work.work_step.step

import com.arellomobile.mvp.InjectViewState
import com.summer.itis.curatorapp.repository.RepositoryProvider
import com.summer.itis.curatorapp.ui.comment.CommentPresenter

@InjectViewState
class  StepPresenter(): CommentPresenter<StepView>() {

    fun loadStep(workId: String, stepId: String) {
        viewState.startTimeout { loadStep(workId, stepId) }
        val disposable = RepositoryProvider.workStepRepository.findById(workId, stepId)
            .subscribe { res ->
                interceptSecondResponse(res, {
                    viewState.stopTimeout()
                    viewState.showStep(it)
                },
                    { loadStep(workId, stepId)})
            }
        compositeDisposable.add(disposable)
    }

}