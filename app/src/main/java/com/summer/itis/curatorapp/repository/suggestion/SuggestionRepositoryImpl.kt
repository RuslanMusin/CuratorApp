package com.summer.itis.curatorapp.repository.suggestion

import android.util.Log
import com.summer.itis.curatorapp.api.services.SuggestionService
import com.summer.itis.curatorapp.model.comment.Comment
import com.summer.itis.curatorapp.model.theme.SuggestionTheme
import com.summer.itis.curatorapp.model.help.SuggestionApi
import com.summer.itis.curatorapp.utils.Const.TAG_LOG
import com.summer.itis.curatorapp.utils.RxUtils
import io.reactivex.Single
import retrofit2.adapter.rxjava2.Result

class SuggestionRepositoryImpl(val apiService: SuggestionService): SuggestionRepository {

    override fun findCuratorSuggestions(id: String): Single<Result<List<SuggestionTheme>>> {
        Log.d(TAG_LOG, "find sugs")
        return apiService.findCuratorSuggestions(id).compose(RxUtils.asyncSingle())
    }

    override fun findCuratorSuggestion(curatorId: String, suggestionsId: String): Single<Result<SuggestionTheme>> {
        return apiService.findCuratorSuggestion(curatorId, suggestionsId).compose(RxUtils.asyncSingle())
    }

    override fun findCuratorSuggestionComments(curatorId: String, suggestionsId: String): Single<Result<List<Comment>>> {
        return apiService.findCuratorSuggestionComments(curatorId, suggestionsId).compose(RxUtils.asyncSingle())
    }

    override fun postCuratorSuggestion(id: String, suggestion: SuggestionApi): Single<Result<SuggestionTheme>> {
        return apiService.postCuratorSuggestion(id, suggestion).compose(RxUtils.asyncSingle())
    }

    override fun updateCuratorSuggestion(curatorId: String, suggestion: SuggestionApi): Single<Result<SuggestionTheme>> {
        return apiService.updateCuratorSuggestion(curatorId, suggestion.id, suggestion).compose(RxUtils.asyncSingle())
    }

    override fun deleteCuratorSuggestion(curatorId: String, suggestionsId: String): Single<Result<SuggestionTheme>> {
        return apiService.deleteCuratorSuggestion(curatorId, suggestionsId).compose(RxUtils.asyncSingle())
    }
}