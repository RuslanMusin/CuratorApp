package com.summer.itis.curatorapp.ui.theme.theme_list.tab_fragment.my_theme_list

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
import com.summer.itis.curatorapp.utils.Const.CURATOR_TYPE
import com.summer.itis.curatorapp.utils.Const.STUDENT_TYPE
import com.summer.itis.curatorapp.utils.Const.WAITING_CURATOR
import com.summer.itis.curatorapp.utils.Const.WAITING_CURATOR_NUM
import io.reactivex.disposables.CompositeDisposable
import java.util.*

@InjectViewState
class MyThemeListPresenter(): BaseFragPresenter<MyThemeListView>() {

    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun addFakeSuggestion(theme: Theme, fakeStudent: Student) {
        val suggestionTheme = Suggestion()
        suggestionTheme.id = "${ Random().nextInt(24000)}"
        suggestionTheme.theme = theme
        suggestionTheme.curator = theme.curator
        suggestionTheme.student = fakeStudent
        suggestionTheme.type = CURATOR_TYPE

        val themeProgress = Progress()
        themeProgress.title = theme.title
        themeProgress.description = theme.description
        suggestionTheme.progress = themeProgress
        suggestionTheme.status = Status(WAITING_CURATOR_NUM, WAITING_CURATOR)
        suggestionTheme.setApiFileds()

        theme.curator?.id?.let { postSuggestion(it, suggestionTheme) }
    }

    fun addFakeStudentSuggestion(theme: Theme, fakeStudent: Student) {
        val suggestionTheme = Suggestion()
        suggestionTheme.id = "${ Random().nextInt(24000)}"
        suggestionTheme.theme = theme
        suggestionTheme.curator = theme.curator
        suggestionTheme.student = fakeStudent
        suggestionTheme.type = STUDENT_TYPE

        val themeProgress = Progress()
        themeProgress.title = theme.title
        themeProgress.description = theme.description
        suggestionTheme.progress = themeProgress
        suggestionTheme.status = Status(WAITING_CURATOR_NUM, WAITING_CURATOR)
        suggestionTheme.setApiFileds()

        theme.curator?.id?.let { postSuggestion(it, suggestionTheme) }
    }

    fun postSuggestion(curatorId: String, suggestion: Suggestion) {
        val disposable = RepositoryProvider.suggestionRepository
            .postCuratorSuggestion(curatorId, suggestion)
            .subscribe { res ->
                interceptSecondResponse(res, {
                    it.status = AppHelper.getStatus(WAITING_CURATOR)
                    RepositoryProvider.suggestionRepository
                        .updateCuratorSuggestion(curatorId, it)
                        .subscribe { res ->
                            interceptSecondResponse(res, {
                                AppHelper.currentCurator.suggestions.add(0, suggestion)
                            },
                                R.string.failed_post_suggestion
                            )
                        }
                },
                    R.string.failed_post_suggestion)
            }
        compositeDisposable.add(disposable)
    }

    fun loadSkills(userId: String) {
        viewState.startTimeout { loadSkills(userId) }
        val disposable = RepositoryProvider.themeRepository
            .findCuratorThemes(userId)
            .subscribe { res ->
                interceptSecondResponse(res, {
                    viewState.stopTimeout()
                    viewState.showThemes(it.reversed())
                }, { loadSkills(userId) })
            }
        compositeDisposable.add(disposable)
    }

    fun loadFakeStudents() {
        viewState.startTimeout(R.string.failed_load_fake_students)
        val disposable = RepositoryProvider.studentRepository
            .findAll()
            .subscribe { res ->
                interceptSecondResponse(res, {
                    viewState.stopTimeout()
                    viewState.updateFakeStudents(it.subList(0, 3))
                },
                    R.string.failed_load_fake_students)
            }
        compositeDisposable.add(disposable)
    }

}