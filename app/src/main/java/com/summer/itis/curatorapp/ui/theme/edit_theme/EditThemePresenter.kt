package com.summer.itis.curatorapp.ui.theme.edit_theme

import android.content.Intent
import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.summer.itis.curatorapp.model.help.ThemeApi
import com.summer.itis.curatorapp.model.theme.Theme
import com.summer.itis.curatorapp.model.user.Student
import com.summer.itis.curatorapp.repository.RepositoryProvider
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragPresenter
import com.summer.itis.curatorapp.utils.AppHelper
import com.summer.itis.curatorapp.utils.Const
import com.summer.itis.curatorapp.utils.Const.THEME_KEY
import com.summer.itis.curatorapp.utils.Const.gsonConverter
import io.reactivex.disposables.CompositeDisposable

@InjectViewState
class EditThemePresenter(): BaseFragPresenter<EditThemeView>() {

    val compositeDisposable: CompositeDisposable = CompositeDisposable()

   /* fun saveThemeEdit(themeNew: Theme) {
        val iterator = AppHelper.currentCurator.themes.iterator()
        for((i, theme) in iterator.withIndex()) {
            if(theme.id.equals(themeNew.id)) {
                AppHelper.currentCurator.themes[i] = themeNew

                val suggestionIterator = AppHelper.currentCurator.suggestions.iterator()
                for(suggestion  in suggestionIterator) {
                    if(suggestion.theme?.id.equals(themeNew.id)) {
                        suggestion.theme = themeNew
                        suggestion.themeProgress?.title = themeNew.title
                        suggestion.themeProgress?.description = themeNew.description
                        suggestion.themeProgress?.skills = themeNew.skills
                    }
                }
                viewState.saveCuratorState()

                val intent = Intent()
                intent.putExtra(THEME_KEY , gsonConverter.toJson(themeNew))
                viewState.returnEditResult(intent)

                break
            }
        }

    }
*/
    fun updateTheme(theme: Theme) {
        val themeApi: ThemeApi = ThemeApi(theme)
        val disposable = RepositoryProvider.themeRepository.updateCuratorTheme(AppHelper.currentCurator.id, themeApi).subscribe { res ->
            Log.d(Const.TAG_LOG, "post theme response")
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
                    Log.d(Const.TAG_LOG, "successful post theme")
                    AppHelper.currentCurator.themes.add(0, theme)
                    val intent = Intent()
                    intent.putExtra(THEME_KEY , gsonConverter.toJson(theme))
                    viewState.returnEditResult(intent)
                } else {
                    Log.d(Const.TAG_LOG, "failed post theme = ${it.code()} and ${it.errorBody()?.string()} and ${it.message()}")
                    Log.d(Const.TAG_LOG, "failed raw = ${it.raw()}")
                }
            }
        }
        compositeDisposable.add(disposable)
    }

}