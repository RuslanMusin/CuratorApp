package com.summer.itis.curatorapp.ui.theme.edit_suggestion

import android.content.Intent
import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.summer.itis.curatorapp.model.theme.SuggestionTheme
import com.summer.itis.curatorapp.repository.RepositoryProvider
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragPresenter
import com.summer.itis.curatorapp.utils.AppHelper
import com.summer.itis.curatorapp.utils.Const
import com.summer.itis.curatorapp.utils.Const.THEME_KEY
import com.summer.itis.curatorapp.utils.Const.gsonConverter
import io.reactivex.disposables.CompositeDisposable

@InjectViewState
class EditSuggestionPresenter(): BaseFragPresenter<EditSuggestionView>() {

    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun updateTheme(suggestion: SuggestionTheme) {
//        val suggestionApi: SuggestionApi = SuggestionApi(suggestion)
        suggestion.setApiFileds()
        val disposable = RepositoryProvider.suggestionRepository.
            updateCuratorProgress(AppHelper.currentCurator.id, suggestion).subscribe { res ->
            Log.d(Const.TAG_LOG, "post suggestion response")
            if(res == null) {
                Log.d(Const.TAG_LOG, "res == null")
            } else {
                if(res.response() == null) {
                    Log.d(Const.TAG_LOG, "response == null and isError = ${res.isError}")
                    Log.d(Const.TAG_LOG, "error = ${res.error()?.message}")
                    res.error()?.printStackTrace()
                }
            }
            res?.response()?.let {
                if (it.isSuccessful) {
                    Log.d(Const.TAG_LOG, "successful post suggestion")
                    val intent = Intent()
                    intent.putExtra(THEME_KEY , gsonConverter.toJson(suggestion.progress))
                    viewState.returnEditResult(intent)
                } else {
                    Log.d(Const.TAG_LOG, "failed post suggestion = ${it.code()} and ${it.errorBody()?.string()} and ${it.message()}")
                    Log.d(Const.TAG_LOG, "failed raw = ${it.raw()}")
                }
            }
        }
        compositeDisposable.add(disposable)
    }

  /*  fun saveSuggestionEdit(progress: ThemeProgress) {
        for(suggestion in AppHelper.currentCurator.suggestions) {
            if(suggestion.progress?.id.equals(progress.id)) {
                suggestion.progress = progress
                viewState.saveCuratorState()

                val intent = Intent()
                intent.putExtra(THEME_KEY , gsonConverter.toJson(progress))
                viewState.returnEditResult(intent)

                break
            }
        }
    }*/

}