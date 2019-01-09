package com.summer.itis.curatorapp.ui.theme.theme_list.tab_fragment.suggestion_list

import android.content.Context
import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.summer.itis.curatorapp.R
import com.summer.itis.curatorapp.model.theme.Status
import com.summer.itis.curatorapp.model.theme.Suggestion
import com.summer.itis.curatorapp.repository.RepositoryProvider
import com.summer.itis.curatorapp.repository.RepositoryProvider.Companion.suggestionRepository
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragPresenter
import com.summer.itis.curatorapp.utils.AppHelper
import com.summer.itis.curatorapp.utils.Const
import com.summer.itis.curatorapp.utils.Const.ACCEPTED_BOTH_NUM
import com.summer.itis.curatorapp.utils.Const.CHANGED_STUDENT
import com.summer.itis.curatorapp.utils.Const.CHANGED_STUDENT_NUM
import com.summer.itis.curatorapp.utils.Const.IN_PROGRESS_CURATOR
import com.summer.itis.curatorapp.utils.Const.IN_PROGRESS_CURATOR_NUM
import com.summer.itis.curatorapp.utils.Const.REJECTED_CURATOR_NUM
import com.summer.itis.curatorapp.utils.Const.REJECTED_STUDENT_NUM
import com.summer.itis.curatorapp.utils.Const.TAG_LOG
import io.reactivex.disposables.CompositeDisposable
import retrofit2.adapter.rxjava2.Result
import java.util.*

@InjectViewState
class SuggestionListPresenter(): BaseFragPresenter<SuggestionListView>() {

    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun loadSuggestions(userId: String) {
        Log.d(TAG_LOG, "id = $userId")
        val disposable = RepositoryProvider.suggestionRepository
            .findCuratorSuggestions(userId)
            .subscribe { res ->
                interceptSecondResponse(res, {
                    viewState.showSuggestions(it.reversed())
                },{ loadSuggestions(userId) })
            }
        compositeDisposable.add(disposable)
    }

    fun setFakeResponse(sug: Suggestion, action: String, context: Context) {
        val curatorId = AppHelper.currentCurator.id
        when(action) {

            context.getString(R.string.accept_theme) -> {

                val iterator = AppHelper.currentCurator.suggestions.iterator()
                for(suggestion in iterator) {
                    if(sug.id.equals(suggestion.id)) {
                        suggestion.status = Status(ACCEPTED_BOTH_NUM, Const.ACCEPTED_BOTH)
                    }
                    else if(suggestion.theme?.id.equals(sug.theme?.id)) {
                        suggestion.status = Status(REJECTED_CURATOR_NUM, Const.REJECTED_CURATOR)
                    }
                }
                for(theme in AppHelper.currentCurator.themes) {
                    if(theme.id.equals(sug.theme?.id)) {
                        theme.dateAcceptance = Date()
                        theme.student = sug.student
                    }
                }
                sug.status = Status(ACCEPTED_BOTH_NUM, Const.ACCEPTED_BOTH)
                sug.setApiFileds()
                suggestionRepository.updateCuratorSuggestion(curatorId, sug).subscribe { e ->
                    Log.d(TAG_LOG, "changed status to accepted")
                    checkResponse(e)
                    loadSuggestions(curatorId)
                }

            }

            context.getString(R.string.reject_theme) -> {
                for(suggestion in AppHelper.currentCurator.suggestions) {
                    if(sug.id.equals(suggestion.id)) {
                        suggestion.status = Status(REJECTED_STUDENT_NUM, Const.REJECTED_STUDENT)
                    }
                }
                sug.status = Status(REJECTED_STUDENT_NUM, Const.REJECTED_STUDENT)
                sug.setApiFileds()
                suggestionRepository.updateCuratorSuggestion(curatorId, sug).subscribe { e ->
                    Log.d(TAG_LOG, "changed status to rejected")
                    checkResponse(e)
                    loadSuggestions(curatorId)
                }
            }

            context.getString(R.string.to_revision) -> {
                for(suggestion in AppHelper.currentCurator.suggestions) {
                    if(sug.id.equals(suggestion.id)) {
                        suggestion.status = Status(IN_PROGRESS_CURATOR, Const.IN_PROGRESS_CURATOR)
                    }
                }
                sug.status = Status(IN_PROGRESS_CURATOR_NUM, Const.IN_PROGRESS_CURATOR)
                sug.setApiFileds()
                suggestionRepository.updateCuratorSuggestion(curatorId, sug).subscribe { e ->
                    Log.d(TAG_LOG, "changed status to in progress")
                    checkResponse(e)
                    loadSuggestions(curatorId)
                }
            }

            context.getString(R.string.save_changes) -> {
                changeSuggestionStatus(sug.id, CHANGED_STUDENT)
                sug.status = Status(CHANGED_STUDENT_NUM, Const.CHANGED_STUDENT)
                sug.setApiFileds()
                suggestionRepository.updateCuratorSuggestion(curatorId, sug).subscribe { e ->
                    Log.d(TAG_LOG, "changed status to save changes")
                    checkResponse(e)
                    loadSuggestions(curatorId)
                }
            }

        }
    }

    fun changeSuggestionStatus(id: String, status: String) {
        for (sug in AppHelper.currentCurator.suggestions) {
            if (sug.id.equals(id)) {
                sug.status = Status(CHANGED_STUDENT_NUM, status)
            }
        }
    }

    fun checkResponse(res: Result<Suggestion>) {
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
                Log.d(Const.TAG_LOG, "successful changed suggestion")
                it.body()?.let { skills ->
                     Log.d(TAG_LOG, "body not null")
                }
            } else {
                Log.d(Const.TAG_LOG, "failed changed suggestion = ${it.code()} and ${it.errorBody()?.string()} and ${it.message()}")
                Log.d(TAG_LOG, "failed raw = ${it.raw()}")

            }
        }
    }

}