package com.summer.itis.curatorapp.ui.work.work_step.step

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.summer.itis.curatorapp.repository.RepositoryProvider
import com.summer.itis.curatorapp.ui.comment.CommentPresenter
import com.summer.itis.curatorapp.utils.Const

@InjectViewState
class  StepPresenter(): CommentPresenter<StepView>() {

    fun loadStep(workId: String, stepId: String) {
        val disposable = RepositoryProvider.workStepRepository.findById(workId, stepId)
            .subscribe { res ->
                interceptResponse(res) { viewState.showStep(it) }
        }
        compositeDisposable.add(disposable)
    }

}