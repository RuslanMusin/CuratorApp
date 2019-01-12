package com.summer.itis.curatorapp.ui.theme.edit_suggestion

import android.content.Intent
import com.arellomobile.mvp.InjectViewState
import com.summer.itis.curatorapp.R
import com.summer.itis.curatorapp.model.theme.Progress
import com.summer.itis.curatorapp.model.theme.Suggestion
import com.summer.itis.curatorapp.repository.RepositoryProvider
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragPresenter
import com.summer.itis.curatorapp.utils.AppHelper
import com.summer.itis.curatorapp.utils.Const.THEME_KEY
import com.summer.itis.curatorapp.utils.Const.gsonConverter
import io.reactivex.disposables.CompositeDisposable

@InjectViewState
class EditSuggestionPresenter(): BaseFragPresenter<EditSuggestionView>() {

    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun updateSuggestion(suggestion: Suggestion) {
        suggestion.setApiFileds()
        viewState.startTimeout (R.string.failed_update_suggestion)
        val disposable = RepositoryProvider.suggestionRepository.
            updateCuratorProgress(AppHelper.currentCurator.id, suggestion).subscribe { res ->
                interceptSecondResponse(res, handleUpdateSuggestion(suggestion),
                    R.string.failed_update_suggestion)
        }
        compositeDisposable.add(disposable)
    }

    private fun handleUpdateSuggestion(suggestion: Suggestion): (progress: Progress) -> Unit {
        return {
            viewState.stopTimeout()
            val intent = Intent()
            intent.putExtra(THEME_KEY , gsonConverter.toJson(suggestion.progress))
            viewState.returnEditResult(intent)
        }
    }

}