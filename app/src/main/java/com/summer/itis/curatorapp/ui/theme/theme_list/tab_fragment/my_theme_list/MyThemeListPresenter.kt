package com.summer.itis.curatorapp.ui.theme.theme_list.tab_fragment.my_theme_list

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
import com.summer.itis.curatorapp.utils.Const.CURATOR_TYPE
import com.summer.itis.curatorapp.utils.Const.STUDENT_TYPE
import com.summer.itis.curatorapp.utils.Const.TAG_LOG
import com.summer.itis.curatorapp.utils.Const.WAITING_CURATOR
import io.reactivex.disposables.CompositeDisposable
import java.util.*

@InjectViewState
class MyThemeListPresenter(): BaseFragPresenter<MyThemeListView>() {

    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun addFakeSuggestion(theme: Theme, fakeStudent: Student) {
        val suggestionTheme = SuggestionTheme()
        suggestionTheme.id = "${ Random().nextInt(24000)}"
        suggestionTheme.theme = theme
        suggestionTheme.curator = theme.curator
        suggestionTheme.student = fakeStudent
        suggestionTheme.type = CURATOR_TYPE

        val themeProgress = ThemeProgress()
        themeProgress.title = theme.title
        themeProgress.description = theme.description
        suggestionTheme.themeProgress = themeProgress
        suggestionTheme.status = Status(Integer.toString(Random().nextInt(100) + 1), WAITING_CURATOR)

        AppHelper.currentCurator.suggestions.add(0, suggestionTheme)

        viewState.saveCuratorState()
    }

    fun addFakeStudentSuggestion(theme: Theme, fakeStudent: Student) {
        val suggestionTheme = SuggestionTheme()
        suggestionTheme.id = "${ Random().nextInt(24000)}"
        suggestionTheme.theme = theme
        suggestionTheme.curator = theme.curator
        suggestionTheme.student = fakeStudent
        suggestionTheme.type = STUDENT_TYPE

        val themeProgress = ThemeProgress()
        themeProgress.title = theme.title
        themeProgress.description = theme.description
        suggestionTheme.themeProgress = themeProgress
        suggestionTheme.status = Status(Integer.toString(Random().nextInt(100) + 1), WAITING_CURATOR)

        AppHelper.currentCurator.suggestions.add(0, suggestionTheme)

        viewState.saveCuratorState()
    }


    fun loadSkills(userId: String) {
        val disposable = RepositoryProvider.themeRepository.findCuratorThemes(userId).subscribe { res ->
            res?.response()?.let {
                if (it.isSuccessful) {
                    Log.d(TAG_LOG, "successful themes")
                    it.body()?.let { themes ->
                        viewState.showThemes(themes)
                    }
                } else {

                }
            }
        }
        compositeDisposable.add(disposable)
    }

    fun findSkillsByType(userId: String, type: String) {
        /* if(type.equals(CURATOR_TYPE)) {
             skillRepository
                     .findMySkills(userId)
         } else {

         }*/
    }

}