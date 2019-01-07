package com.summer.itis.curatorapp.ui.theme.add_theme

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.summer.itis.curatorapp.model.theme.Status
import com.summer.itis.curatorapp.model.theme.Suggestion
import com.summer.itis.curatorapp.model.theme.Theme
import com.summer.itis.curatorapp.model.theme.Progress
import com.summer.itis.curatorapp.model.user.Student
import com.summer.itis.curatorapp.repository.RepositoryProvider
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragPresenter
import com.summer.itis.curatorapp.utils.AppHelper
import com.summer.itis.curatorapp.utils.Const
import com.summer.itis.curatorapp.utils.Const.CURATOR_TYPE
import com.summer.itis.curatorapp.utils.Const.ONE_CHOOSED
import com.summer.itis.curatorapp.utils.Const.TAG_LOG
import com.summer.itis.curatorapp.utils.Const.WAITING_STUDENT
import com.summer.itis.curatorapp.utils.Const.WAITING_STUDENT_NUM
import io.reactivex.disposables.CompositeDisposable
import java.util.*

@InjectViewState
class AddThemePresenter(): BaseFragPresenter<AddThemeView>() {

    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun postTheme(theme: Theme) {
        val curatorId = AppHelper.currentCurator.id
        var student: Student? = null

        if(theme.student != null) {
            student = theme.student
            theme.targetType = ONE_CHOOSED
            theme.student = null
        }
        val disposable = RepositoryProvider.themeRepository.postCuratorTheme(curatorId, theme).subscribe { res ->
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
                        viewState.getResultAfterAdd()
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
        val suggestionTheme = Suggestion()
        suggestionTheme.id = "${Random().nextInt(24000) + 100}"
        suggestionTheme.theme = theme
        suggestionTheme.curator = theme.curator
        suggestionTheme.student = student
        suggestionTheme.status = Status(WAITING_STUDENT_NUM, WAITING_STUDENT)
        suggestionTheme.type = CURATOR_TYPE

        val themeProgress = Progress()
        themeProgress.title = theme.title
        themeProgress.description = theme.description
        suggestionTheme.progress = themeProgress
        suggestionTheme.setApiFileds()

        val disposable = RepositoryProvider.suggestionRepository.postCuratorSuggestion(curatorId, suggestionTheme).subscribe { res ->
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
                        viewState.getResultAfterAdd()
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