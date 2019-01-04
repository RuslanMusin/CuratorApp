package com.summer.itis.curatorapp.ui.theme.suggestion_item

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.summer.itis.curatorapp.model.theme.Status
import com.summer.itis.curatorapp.model.theme.SuggestionTheme
import com.summer.itis.curatorapp.repository.RepositoryProvider
import com.summer.itis.curatorapp.ui.comment.CommentPresenter
import com.summer.itis.curatorapp.utils.AppHelper
import com.summer.itis.curatorapp.utils.Const
import com.summer.itis.curatorapp.utils.Const.ACCEPTED_BOTH
import com.summer.itis.curatorapp.utils.Const.ACCEPTED_BOTH_NUM
import com.summer.itis.curatorapp.utils.Const.CHANGED_CURATOR
import com.summer.itis.curatorapp.utils.Const.IN_PROGRESS_CURATOR
import com.summer.itis.curatorapp.utils.Const.IN_PROGRESS_STUDENT
import com.summer.itis.curatorapp.utils.Const.REJECTED_CURATOR
import com.summer.itis.curatorapp.utils.Const.WAITING_CURATOR
import retrofit2.adapter.rxjava2.Result
import java.util.*

@InjectViewState
class  SuggestionPresenter(): CommentPresenter<SuggestionView>() {

    fun acceptTheme(sug: SuggestionTheme) {
        val curatorId = AppHelper.currentCurator.id
        sug.status = AppHelper.getStatus(ACCEPTED_BOTH)
        changeSuggestionStatus(curatorId, sug)
    }

    fun rejectCurator(sug: SuggestionTheme) {
       /* for (sug in AppHelper.currentCurator.suggestions) {
            if (sug.id.equals(sug.id)) {
                sug.status = Status(Integer.toString(Random().nextInt(100) + 1), REJECTED_CURATOR)
                viewState.setStatus(sug.status.name)

            }
        }*/
        val curatorId = AppHelper.currentCurator.id
        sug.status = AppHelper.getStatus(REJECTED_CURATOR)
        changeSuggestionStatus(curatorId, sug)
    }

    fun revisionTheme (sug: SuggestionTheme) {
        val curatorId = AppHelper.currentCurator.id
        when (sug.status.name) {

            IN_PROGRESS_CURATOR -> {
                sug.status = AppHelper.getStatus(CHANGED_CURATOR)
                changeSuggestionStatus(curatorId, sug)
            }

            WAITING_CURATOR -> {
                sug.status = AppHelper.getStatus(IN_PROGRESS_STUDENT)
                changeSuggestionStatus(curatorId, sug)            }
        }
    }

    fun changeSuggestionStatus(curatorId: String, sug: SuggestionTheme) {
        /*for (sug in AppHelper.currentCurator.suggestions) {
            if (sug.id.equals(id)) {
                sug.status = Status(Integer.toString(Random().nextInt(100) + 1), status)
                viewState.setStatus(status)
            }
        }*/
        viewState.setStatus(sug.status.name)
        sug.setApiFileds()
        RepositoryProvider.suggestionRepository.updateCuratorSuggestion(curatorId, sug).subscribe { e ->
            Log.d(Const.TAG_LOG, "changed status to rejected")
            checkResponse(e)
        }
    }

    fun checkResponse(res: Result<SuggestionTheme>) {
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
                    Log.d(Const.TAG_LOG, "body not null")
                }
            } else {
                Log.d(Const.TAG_LOG, "failed changed suggestion = ${it.code()} and ${it.errorBody()?.string()} and ${it.message()}")
                Log.d(Const.TAG_LOG, "failed raw = ${it.raw()}")

            }
        }
    }

}