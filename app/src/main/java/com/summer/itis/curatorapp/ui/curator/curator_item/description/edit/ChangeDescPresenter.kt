package com.summer.itis.curatorapp.ui.curator.curator_item.description.edit

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.summer.itis.curatorapp.model.help.CuratorApi
import com.summer.itis.curatorapp.model.user.Curator
import com.summer.itis.curatorapp.repository.RepositoryProvider
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragPresenter
import com.summer.itis.curatorapp.utils.AppHelper
import com.summer.itis.curatorapp.utils.Const
import com.summer.itis.curatorapp.utils.Const.CURATOR_TYPE
import com.summer.itis.curatorapp.utils.Const.SUGGESTION_TYPE
import com.summer.itis.curatorapp.utils.Const.THEME_TYPE
import io.reactivex.disposables.CompositeDisposable

@InjectViewState
class ChangeDescPresenter(): BaseFragPresenter<ChangeDescView>() {

    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun saveDescription(description: String, type: String, id: String?) {

        when(type) {

//            CURATOR_TYPE -> saveCuratorDesc(description)

            SUGGESTION_TYPE -> id?.let { saveSuggestionDesc(description, it) }

            THEME_TYPE -> id?.let { saveThemeDesc(description, it) }
        }
    }

    fun saveThemeDesc(description: String, id: String) {
        val themes = AppHelper.currentCurator.themes
        for(theme in themes) {
            if(theme.id.equals(id)) {
                theme.description = description
                viewState.saveCuratorState()
            }
        }
    }

    fun saveSuggestionDesc(description: String, id: String) {
        val suggestions = AppHelper.currentCurator.suggestions
        for(suggestionTheme in suggestions) {
            if(suggestionTheme.id.equals(id)) {
                suggestionTheme.themeProgress?.description = description
                viewState.saveCuratorState()
            }
        }
    }

    fun saveCuratorDesc(curator: Curator) {
        val curatorApi = CuratorApi(curator)
        val disposable = RepositoryProvider.curatorRepository.update(curator.id, curatorApi).subscribe { res ->
            Log.d(Const.TAG_LOG, "receive subjects response")
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
                    Log.d(Const.TAG_LOG, "successful subjects")
                    it.body()?.let { students ->
                        AppHelper.currentCurator.description = curator.description
                        viewState.saveCuratorState()
                        viewState.showChanges()
                    }
                } else {
                    Log.d(Const.TAG_LOG, "failed subjects")

                }
            }
        }
        compositeDisposable.add(disposable)

    }

    fun loadStudent(studentId: String) {

    }
}