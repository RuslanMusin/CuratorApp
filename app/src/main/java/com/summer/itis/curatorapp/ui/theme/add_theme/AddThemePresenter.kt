package com.summer.itis.curatorapp.ui.theme.add_theme

import com.arellomobile.mvp.InjectViewState
import com.summer.itis.curatorapp.model.theme.Status
import com.summer.itis.curatorapp.model.theme.Suggestion
import com.summer.itis.curatorapp.model.theme.Theme
import com.summer.itis.curatorapp.model.theme.Progress
import com.summer.itis.curatorapp.model.user.Student
import com.summer.itis.curatorapp.repository.RepositoryProvider
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragPresenter
import com.summer.itis.curatorapp.utils.AppHelper
import com.summer.itis.curatorapp.utils.Const.CURATOR_TYPE
import com.summer.itis.curatorapp.utils.Const.ONE_CHOOSED
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
        val disposable = RepositoryProvider.themeRepository
            .postCuratorTheme(curatorId, theme)
            .subscribe { res ->
                interceptResponse(res, handlePostSuggestion(theme, student, curatorId))
        }
        compositeDisposable.add(disposable)
    }

    private fun handlePostSuggestion(theme: Theme, student: Student?, curatorId: String): (theme: Theme) -> Unit {
        return {
            theme.id = it.id
            AppHelper.currentCurator.themes.add(0, theme)
            if(student != null) {
                postSuggestion(curatorId, theme, student)
            } else {
                viewState.getResultAfterAdd()
            }
        }
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

        val disposable = RepositoryProvider.suggestionRepository
            .postCuratorSuggestion(curatorId, suggestionTheme)
            .subscribe { res ->
                interceptResponse(res, handlePostSuggestion())
        }
        compositeDisposable.add(disposable)
    }

    private fun handlePostSuggestion(): (suggestion: Suggestion) -> Unit {
        return {
            AppHelper.currentCurator.suggestions.add(0, it)
            viewState.getResultAfterAdd()
        }
    }


}