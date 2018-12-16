package com.summer.itis.curatorapp.repository.suggestion

import com.summer.itis.curatorapp.model.comment.Comment
import com.summer.itis.curatorapp.model.theme.SuggestionTheme
import com.summer.itis.curatorapp.model.help.SuggestionApi
import io.reactivex.Single
import retrofit2.adapter.rxjava2.Result

interface SuggestionRepository {
    fun findCuratorSuggestions(id: String): Single<Result<List<SuggestionTheme>>>
    fun findCuratorSuggestion(curatorId: String, suggestionsId: String): Single<Result<SuggestionTheme>>
    fun findCuratorSuggestionComments(curatorId: String, suggestionsId: String): Single<Result<List<Comment>>>

    fun postCuratorSuggestion(id: String, suggestion: SuggestionApi): Single<Result<SuggestionTheme>>
    fun updateCuratorSuggestion(curatorId: String, suggestion: SuggestionApi): Single<Result<SuggestionTheme>>
    fun deleteCuratorSuggestion(curatorId: String, suggestionsId: String): Single<Result<SuggestionTheme>>
}