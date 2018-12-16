package com.summer.itis.curatorapp.ui.theme.add_theme

import android.content.Context
import android.content.Intent
import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.summer.itis.curatorapp.model.theme.Status
import com.summer.itis.curatorapp.model.theme.SuggestionTheme
import com.summer.itis.curatorapp.model.theme.Theme
import com.summer.itis.curatorapp.model.theme.ThemeProgress
import com.summer.itis.curatorapp.model.help.SuggestionApi
import com.summer.itis.curatorapp.model.help.ThemeApi
import com.summer.itis.curatorapp.model.user.Student
import com.summer.itis.curatorapp.repository.RepositoryProvider
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragPresenter
import com.summer.itis.curatorapp.utils.AppHelper
import com.summer.itis.curatorapp.utils.Const
import com.summer.itis.curatorapp.utils.Const.ADD_THEME_TYPE
import com.summer.itis.curatorapp.utils.Const.CURATOR_TYPE
import com.summer.itis.curatorapp.utils.Const.ONE_CHOOSED
import com.summer.itis.curatorapp.utils.Const.SUGGESTION_TYPE
import com.summer.itis.curatorapp.utils.Const.TAG_LOG
import com.summer.itis.curatorapp.utils.Const.THEME_KEY
import com.summer.itis.curatorapp.utils.Const.THEME_TYPE
import com.summer.itis.curatorapp.utils.Const.gsonConverter
import io.reactivex.disposables.CompositeDisposable
import java.util.*

@InjectViewState
class AddThemePresenter(): BaseFragPresenter<AddThemeView>() {

    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun saveChanges(theme: Theme, type: String, context: Context) {
        when(type) {
            ADD_THEME_TYPE -> addTheme(theme)

            THEME_TYPE -> saveThemeEdit(theme)

            SUGGESTION_TYPE -> saveSuggestionEdit(theme,context)
        }
    }

    fun saveSuggestionEdit(theme: Theme, context: Context) {
        for(suggestion in AppHelper.currentCurator.suggestions) {
            if(suggestion.themeId.equals(theme.id)) {
                val themeProgress = suggestion.themeProgress
                themeProgress?.title = theme.title
                themeProgress?.description = theme.description
                themeProgress?.subject = theme.subject
                AppHelper.saveCurrentState(AppHelper.currentCurator, context)

                val intent = Intent()
                intent.putExtra(THEME_KEY , gsonConverter.toJson(themeProgress))
                viewState.getResultAfterEdit(true, intent)
            }
        }
    }

    fun saveThemeEdit(themeNew: Theme) {
        val iterator = AppHelper.currentCurator.themes.iterator()
        for((i, theme) in iterator.withIndex()) {
            if(theme.id.equals(themeNew.id)) {
                AppHelper.currentCurator.themes[i] = themeNew
                viewState.saveCuratorState()

                val intent = Intent()
                intent.putExtra(THEME_KEY , gsonConverter.toJson(themeNew))
                viewState.getResultAfterEdit(true, intent)
            }
        }

    }

    fun addTheme(theme: Theme) {
        postTheme(AppHelper.currentCurator.id, theme)
        /*if(theme.student != null) {
            val suggestionTheme = SuggestionTheme()
            suggestionTheme.id = "${Random().nextInt(24000)}"
            suggestionTheme.theme = theme
            suggestionTheme.curator = theme.curator
            suggestionTheme.student = theme.student
            suggestionTheme.type = CURATOR_TYPE

            val themeProgress = ThemeProgress()
            themeProgress.title = theme.title
            themeProgress.description = theme.description
            suggestionTheme.themeProgress = themeProgress
            suggestionTheme.status = Status(Integer.toString(Random().nextInt(100) + 1), Const.WAITING_STUDENT)

            theme.targetType = ONE_CHOOSED
            theme.student = null

            AppHelper.currentCurator.suggestions.add(0, suggestionTheme)
        }


        viewState.getResultAfterEdit(false, null)*/
    }

    fun postTheme(curatorId: String, theme: Theme) {
        var student: Student? = null

        if(theme.student != null) {
            student = theme.student
            theme.targetType = ONE_CHOOSED
            theme.student = null
        }
        val themeApi: ThemeApi = ThemeApi(theme)
        val disposable = RepositoryProvider.themeRepository.postCuratorTheme(curatorId, themeApi).subscribe { res ->
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
                    it.body()?.let { theme.id = it.id }
                    AppHelper.currentCurator.themes.add(0, theme)
                    if(student != null) {
                        postSuggestion(curatorId, theme, student)
                    } else {
                    it.body()?.let { skills ->
                        viewState.getResultAfterEdit(false, null)
                    }
                    }
                } else {
                    Log.d(Const.TAG_LOG, "failed post theme = ${it.code()} and ${it.errorBody()?.string()} and ${it.message()}")
                        Log.d(TAG_LOG, "failed raw = ${it.raw()}")
                }
            }
        }
        compositeDisposable.add(disposable)
    }

    fun postSuggestion(curatorId: String, theme: Theme, student: Student) {
        val suggestionTheme = SuggestionTheme()
        suggestionTheme.id = "${Random().nextInt(24000) + 100}"
        suggestionTheme.theme = theme
        suggestionTheme.curator = theme.curator
        suggestionTheme.student = student
        suggestionTheme.type = CURATOR_TYPE
        suggestionTheme.dateCreation = theme.dateCreation

        val themeProgress = ThemeProgress()
        themeProgress.title = theme.title
        themeProgress.description = theme.description
        suggestionTheme.themeProgress = themeProgress
        suggestionTheme.status = Status(Integer.toString(Random().nextInt(3) + 1), Const.WAITING_STUDENT)

        val suggestionApi = SuggestionApi(suggestionTheme)

        val disposable = RepositoryProvider.suggestionRepository.postCuratorSuggestion(curatorId, suggestionApi).subscribe { res ->
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
                    it.body()?.let { skills ->
                        AppHelper.currentCurator.suggestions.add(0, suggestionTheme)
                        viewState.getResultAfterEdit(false, null)
                    }
                } else {
                    Log.d(Const.TAG_LOG, "failed post suggestion = ${it.code()} and ${it.errorBody()?.string()} and ${it.message()}")
                    Log.d(TAG_LOG, "failed raw = ${it.raw()}")

                }
            }
        }
        compositeDisposable.add(disposable)
    }


}