package com.summer.itis.curatorapp.repository.suggestion

import com.summer.itis.curatorapp.model.comment.Comment
import com.summer.itis.curatorapp.model.theme.Suggestion
import com.summer.itis.curatorapp.model.theme.Progress
import io.reactivex.Single
import retrofit2.adapter.rxjava2.Result

interface SuggestionRepository {
    fun findCuratorSuggestions(id: String): Single<Result<List<Suggestion>>>
    fun findCuratorSuggestion(curatorId: String, suggestionsId: String): Single<Result<Suggestion>>
    fun findCuratorSuggestionComments(curatorId: String, suggestionsId: String): Single<Result<List<Comment>>>

    fun postCuratorSuggestion(id: String, suggestion: Suggestion): Single<Result<Suggestion>>
    fun updateCuratorSuggestion(curatorId: String, suggestion: Suggestion): Single<Result<Suggestion>>
    fun deleteCuratorSuggestion(curatorId: String, suggestionsId: String): Single<Result<Suggestion>>
    fun updateCuratorProgress(curatorId: String, suggestion: Suggestion): Single<Result<Progress>>

}