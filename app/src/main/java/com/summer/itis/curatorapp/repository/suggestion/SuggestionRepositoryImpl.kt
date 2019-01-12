package com.summer.itis.curatorapp.repository.suggestion

import android.util.Log
import com.summer.itis.curatorapp.api.services.SuggestionService
import com.summer.itis.curatorapp.model.comment.Comment
import com.summer.itis.curatorapp.model.theme.Suggestion
import com.summer.itis.curatorapp.model.theme.Progress
import com.summer.itis.curatorapp.utils.Const.TAG_LOG
import com.summer.itis.curatorapp.utils.RxUtils
import io.reactivex.Single
import retrofit2.adapter.rxjava2.Result

class SuggestionRepositoryImpl(val apiService: SuggestionService): SuggestionRepository {

    override fun findCuratorSuggestions(id: String): Single<Result<List<Suggestion>>> {
        Log.d(TAG_LOG, "find sugs")
        return apiService.findCuratorSuggestions(id).compose(RxUtils.asyncSingle())
    }

    override fun findCuratorSuggestion(curatorId: String, suggestionsId: String): Single<Result<Suggestion>> {
        return apiService.findCuratorSuggestion(curatorId, suggestionsId).compose(RxUtils.asyncSingle())
    }

    override fun findCuratorSuggestionComments(curatorId: String, suggestionsId: String): Single<Result<List<Comment>>> {
        return apiService.findCuratorSuggestionComments(curatorId, suggestionsId).compose(RxUtils.asyncSingle())
    }

    override fun postCuratorSuggestion(id: String, suggestion: Suggestion): Single<Result<Suggestion>> {
        return apiService.postCuratorSuggestion(id, suggestion).compose(RxUtils.asyncSingle())
    }

    override fun updateCuratorSuggestion(curatorId: String, suggestion: Suggestion): Single<Result<Suggestion>> {
        return apiService.updateCuratorSuggestion(curatorId, suggestion.id, suggestion).compose(RxUtils.asyncSingle())
    }

    override fun deleteCuratorSuggestion(curatorId: String, suggestionsId: String): Single<Result<Suggestion>> {
        return apiService.deleteCuratorSuggestion(curatorId, suggestionsId).compose(RxUtils.asyncSingle())
    }

    override fun updateCuratorProgress(curatorId: String, suggestion: Suggestion): Single<Result<Progress>> {
        return apiService.updateCuratorProgress(curatorId, suggestion.id, suggestion.progress).compose(RxUtils.asyncSingle())
    }
}