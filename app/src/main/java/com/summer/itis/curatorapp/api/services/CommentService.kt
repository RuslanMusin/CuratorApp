package com.summer.itis.curatorapp.api.services

import com.summer.itis.curatorapp.model.comment.Comment
import io.reactivex.Single
import retrofit2.adapter.rxjava2.Result
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CommentService {

    @GET("curators/{curator_id}/works/{work_id}/steps/{step_id}/comments")
    fun findStepComments(
        @Path(value = "curator_id") curatorId: String,
        @Path(value = "work_id") workId: String,
        @Path(value = "step_id") stepId: String
    ): Single<Result<List<Comment>>>

    @POST("curators/{curator_id}/works/{work_id}/steps/{step_id}/comments")
    fun postStepComment(
        @Path(value = "curator_id") curatorId: String,
        @Path(value = "work_id") workId: String,
        @Path(value = "step_id") stepId: String,
        @Body comment: Comment
    ): Single<Result<Comment>>

    @GET("curators/{curator_id}/suggestions/{suggestion_id}/comments")
    fun findSuggestionComments(
        @Path(value = "curator_id") curatorId: String,
        @Path(value = "suggestion_id") suggestionId: String
    ): Single<Result<List<Comment>>>

    @POST("curators/{curator_id}/suggestions/{suggestion_id}/comments")
    fun postSuggestionComment(
        @Path(value = "curator_id") curatorId: String,
        @Path(value = "suggestion_id") suggestionId: String,
        @Body comment: Comment
    ): Single<Result<Comment>>

}