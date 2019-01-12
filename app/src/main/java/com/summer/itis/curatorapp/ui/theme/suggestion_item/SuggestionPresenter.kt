package com.summer.itis.curatorapp.ui.theme.suggestion_item

import com.arellomobile.mvp.InjectViewState
import com.summer.itis.curatorapp.R
import com.summer.itis.curatorapp.model.theme.Suggestion
import com.summer.itis.curatorapp.repository.RepositoryProvider
import com.summer.itis.curatorapp.ui.comment.CommentPresenter
import com.summer.itis.curatorapp.utils.AppHelper
import com.summer.itis.curatorapp.utils.Const.ACCEPTED_BOTH
import com.summer.itis.curatorapp.utils.Const.CHANGED_CURATOR
import com.summer.itis.curatorapp.utils.Const.IN_PROGRESS_CURATOR
import com.summer.itis.curatorapp.utils.Const.IN_PROGRESS_STUDENT
import com.summer.itis.curatorapp.utils.Const.REJECTED_CURATOR
import com.summer.itis.curatorapp.utils.Const.WAITING_CURATOR

@InjectViewState
class  SuggestionPresenter(): CommentPresenter<SuggestionView>() {

    fun acceptTheme(sug: Suggestion) {
        val curatorId = AppHelper.currentCurator.id
        sug.status = AppHelper.getStatus(ACCEPTED_BOTH)
        changeSuggestionStatus(curatorId, sug)
    }

    fun rejectCurator(sug: Suggestion) {
        val curatorId = AppHelper.currentCurator.id
        sug.status = AppHelper.getStatus(REJECTED_CURATOR)
        changeSuggestionStatus(curatorId, sug)
    }

    fun revisionTheme (sug: Suggestion) {
        val curatorId = AppHelper.currentCurator.id
        when (sug.status.name) {

            IN_PROGRESS_CURATOR -> {
                sug.status = AppHelper.getStatus(CHANGED_CURATOR)
                viewState.hideEdit(sug.status)
                changeSuggestionStatus(curatorId, sug)
            }

            WAITING_CURATOR -> {
                sug.status = AppHelper.getStatus(IN_PROGRESS_STUDENT)
                changeSuggestionStatus(curatorId, sug)
            }
        }
    }

    fun changeSuggestionStatus(curatorId: String, sug: Suggestion) {
        sug.setApiFileds()
        viewState.startTimeout ( R.string.failed_update_status)
        val disposable = RepositoryProvider.suggestionRepository
            .updateCuratorSuggestion(curatorId, sug)
            .subscribe { res ->
                interceptSecondResponse(res, {
                    viewState.stopTimeout()
                    viewState.setStatus(sug.status.name)
                },
                    R.string.failed_update_status)
            }
        compositeDisposable.add(disposable)
    }

}