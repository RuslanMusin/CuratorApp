package com.summer.itis.curatorapp.ui.theme.theme_item

import com.arellomobile.mvp.InjectViewState
import com.summer.itis.curatorapp.R
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
import com.summer.itis.curatorapp.utils.Const.WAITING_STUDENT_NUM
import io.reactivex.disposables.CompositeDisposable
import java.util.*

@InjectViewState
class  ThemePresenter(): BaseFragPresenter<ThemeView>() {

    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun sendSuggestion(theme: Theme, student: Student) {
        val suggestionTheme = Suggestion()
        suggestionTheme.id = "${Random().nextInt(24000)}"
        suggestionTheme.type = CURATOR_TYPE
        suggestionTheme.student = student
        suggestionTheme.curator = AppHelper.currentCurator
        suggestionTheme.status = Status(WAITING_STUDENT_NUM, Const.WAITING_STUDENT)
        suggestionTheme.theme = theme
        suggestionTheme.setApiFileds()

        val themeProgress = Progress()
        themeProgress.title = theme.title
        themeProgress.description = theme.description

        suggestionTheme.progress = themeProgress

        val disposable =
                RepositoryProvider.suggestionRepository
                    .postCuratorSuggestion(AppHelper.currentCurator.id, suggestionTheme)
                    .subscribe { res ->
                        interceptSecondResponse(res, {
                            AppHelper.currentCurator.suggestions.add(0, suggestionTheme)
                        },
                            R.string.failed_send_suggestion)
                    }
        compositeDisposable.add(disposable)
    }

}