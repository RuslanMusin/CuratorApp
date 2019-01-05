package com.summer.itis.curatorapp.ui.theme.theme_item

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.summer.itis.curatorapp.model.theme.Status
import com.summer.itis.curatorapp.model.theme.SuggestionTheme
import com.summer.itis.curatorapp.model.theme.Theme
import com.summer.itis.curatorapp.model.theme.ThemeProgress
import com.summer.itis.curatorapp.model.user.Student
import com.summer.itis.curatorapp.repository.RepositoryProvider
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragPresenter
import com.summer.itis.curatorapp.utils.AppHelper
import com.summer.itis.curatorapp.utils.Const
import com.summer.itis.curatorapp.utils.Const.CURATOR_TYPE
import com.summer.itis.curatorapp.utils.Const.WAITING_STUDENT_NUM
import io.reactivex.disposables.CompositeDisposable
import java.util.*

@InjectViewState
class  ThemePresenter(): BaseFragPresenter<ThemeView>() {

    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun sendSuggestion(theme: Theme, student: Student) {
        val suggestionTheme = SuggestionTheme()
        suggestionTheme.id = "${Random().nextInt(24000)}"
        suggestionTheme.type = CURATOR_TYPE
        suggestionTheme.student = student
        suggestionTheme.curator = AppHelper.currentCurator
        suggestionTheme.status = Status(WAITING_STUDENT_NUM, Const.WAITING_STUDENT)
        suggestionTheme.theme = theme
        suggestionTheme.setApiFileds()

        val themeProgress = ThemeProgress()
        themeProgress.title = theme.title
        themeProgress.description = theme.description

        suggestionTheme.progress = themeProgress

        val disposable =
                RepositoryProvider.suggestionRepository.postCuratorSuggestion(AppHelper.currentCurator.id, suggestionTheme).subscribe { res ->
                    Log.d(Const.TAG_LOG, "post suggestion response")
                    if (res == null) {
                        Log.d(Const.TAG_LOG, "res == null")
                    } else {
                        if (res.response() == null) {
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
                                viewState.saveCuratorState()

                            }
                        } else {
                            Log.d(
                                Const.TAG_LOG,
                                "failed post suggestion = ${it.code()} and ${it.errorBody()?.string()} and ${it.message()}"
                            )
                            Log.d(Const.TAG_LOG, "failed raw = ${it.raw()}")

                        }
                    }
                }
        compositeDisposable.add(disposable)
    }

}