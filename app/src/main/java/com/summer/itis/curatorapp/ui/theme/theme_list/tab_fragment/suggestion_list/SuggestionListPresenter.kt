package com.summer.itis.curatorapp.ui.theme.theme_list.tab_fragment.suggestion_list

import android.content.Context
import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.summer.itis.curatorapp.R
import com.summer.itis.curatorapp.model.theme.Status
import com.summer.itis.curatorapp.model.theme.SuggestionTheme
import com.summer.itis.curatorapp.model.work.Work
import com.summer.itis.curatorapp.repository.RepositoryProvider
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragPresenter
import com.summer.itis.curatorapp.utils.AppHelper
import com.summer.itis.curatorapp.utils.Const
import com.summer.itis.curatorapp.utils.Const.ACCEPTED_BOTH
import com.summer.itis.curatorapp.utils.Const.CHANGED_STUDENT
import com.summer.itis.curatorapp.utils.Const.IN_PROGRESS_CURATOR
import com.summer.itis.curatorapp.utils.Const.REJECTED_CURATOR
import com.summer.itis.curatorapp.utils.Const.REJECTED_STUDENT
import com.summer.itis.curatorapp.utils.Const.TAG_LOG
import io.reactivex.disposables.CompositeDisposable
import java.util.*

@InjectViewState
class SuggestionListPresenter(): BaseFragPresenter<SuggestionListView>() {

    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun loadSkills(userId: String) {
        Log.d(TAG_LOG, "id = $userId")
        val disposable = RepositoryProvider.suggestionRepository.findCuratorSuggestions(userId).subscribe { res ->
            Log.d(TAG_LOG, "receive sug response")
            if(res == null) {
                Log.d(Const.TAG_LOG, "res == null")
            } else {
                if(res.response() == null) {
                    Log.d(Const.TAG_LOG, "response == null and isError = ${res.isError}")
                    Log.d(TAG_LOG, "error = ${res.error()?.message}")
                    res.error()?.printStackTrace()
                }
            }
            res?.response()?.let {
                if (it.isSuccessful) {
                    Log.d(Const.TAG_LOG, "successful suggestions")
                    it.body()?.let { suggestions ->
                        viewState.showSuggestions(suggestions)
                    }
                } else {
                    Log.d(Const.TAG_LOG, "failed suggestions")

                }
            }
        }
        compositeDisposable.add(disposable)
    }

    fun setFakeResponse(sug: SuggestionTheme, action: String, context: Context) {
        when(action) {

            context.getString(R.string.accept_theme) -> {
                val work = Work()
                work.id = sug.id
                work.dateStart = Date()
                sug.theme?.let {
                    it.student = sug.student
                    it.curator = sug.curator
                    work.theme = it
                }
                AppHelper.currentCurator.works.add(0, work)
                val iterator = AppHelper.currentCurator.suggestions.iterator()
                for(suggestion in iterator) {
                    if(sug.id.equals(suggestion.id)) {
                        suggestion.status = Status(Integer.toString(Random().nextInt(100) + 1), Const.ACCEPTED_BOTH)
                    }
                    else if(suggestion.theme?.id.equals(sug.theme?.id)) {
                        suggestion.status = Status(Integer.toString(Random().nextInt(100) + 1), Const.REJECTED_CURATOR)
                    }
                }
                for(theme in AppHelper.currentCurator.themes) {
                    if(theme.id.equals(sug.theme?.id)) {
                        theme.dateAcceptance = Date()
                        theme.student = sug.student
                    }
                }
                viewState.changeDataSet(AppHelper.currentCurator.suggestions)
                viewState.saveCuratorState()
            }

            context.getString(R.string.reject_theme) -> {
                for(suggestion in AppHelper.currentCurator.suggestions) {
                    if(sug.id.equals(suggestion.id)) {
                        suggestion.status = Status(Integer.toString(Random().nextInt(100) + 1), Const.REJECTED_STUDENT)
                    }
                }
                viewState.changeDataSet(AppHelper.currentCurator.suggestions)
                viewState.saveCuratorState()
            }

            context.getString(R.string.to_revision) -> {
                for(suggestion in AppHelper.currentCurator.suggestions) {
                    if(sug.id.equals(suggestion.id)) {
                        suggestion.status = Status(Integer.toString(Random().nextInt(100) + 1), Const.IN_PROGRESS_CURATOR)
                    }
                }
                viewState.changeDataSet(AppHelper.currentCurator.suggestions)
                viewState.saveCuratorState()
            }

            context.getString(R.string.save_changes) -> {
                changeSuggestionStatus(sug.id, CHANGED_STUDENT)
            }

        }
    }

    fun changeSuggestionStatus(id: String, status: String) {
        for (sug in AppHelper.currentCurator.suggestions) {
            if (sug.id.equals(id)) {
                sug.status = Status(Integer.toString(Random().nextInt(100) + 1), status)
            }
        }
        viewState.changeDataSet(AppHelper.currentCurator.suggestions)
        viewState.saveCuratorState()
    }

    fun findSkillsByType(userId: String, type: String) {
        /* if(type.equals(CURATOR_TYPE)) {
             skillRepository
                     .findMySkills(userId)
         } else {

         }*/
    }

}