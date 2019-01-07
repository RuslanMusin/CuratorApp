package com.summer.itis.curatorapp.api.services

import com.summer.itis.curatorapp.model.comment.Comment
import com.summer.itis.curatorapp.model.theme.Suggestion
import com.summer.itis.curatorapp.model.theme.Progress
import io.reactivex.Single
import retrofit2.adapter.rxjava2.Result
import retrofit2.http.*

interface SuggestionService {

    @GET("curators/{curator_id}/suggestions")
    fun findCuratorSuggestions(@Path(value = "curator_id") id: String): Single<Result<List<Suggestion>>>

    @GET("curators/{curator_id}/suggestions/{suggestion_id}")
    fun findCuratorSuggestion(@Path(value = "curator_id") curatorId: String,
                              @Path(value = "suggestion_id") suggestionId: String): Single<Result<Suggestion>>

    @POST("curators/{curator_id}/suggestions")
    fun postCuratorSuggestion(@Path(value = "curator_id") id: String,
                              @Body suggestion: Suggestion
    ): Single<Result<Suggestion>>

    @GET("curators/{curator_id}/suggestions/{suggestion_id}/comments")
    fun findCuratorSuggestionComments(@Path(value = "curator_id") curatorId: String,
                                      @Path(value = "suggestion_id") suggestionId: String): Single<Result<List<Comment>>>

    @DELETE("curators/{curator_id}/suggestions/{suggestion_id}")
    fun deleteCuratorSuggestion(@Path(value = "curator_id") curatorId: String,
                                @Path(value = "suggestion_id") suggestionId: String): Single<Result<Suggestion>>

    @PUT("curators/{curator_id}/suggestions/{suggestion_id}")
    fun updateCuratorSuggestion(@Path(value = "curator_id") curatorId: String,
                                @Path(value = "suggestion_id") suggestionId: String,
                                @Body suggestion: Suggestion
    ): Single<Result<Suggestion>>

    @POST("curators/{curator_id}/suggestions/{suggestion_id}/comments")
    fun postCuratorSuggestionComment(@Path(value = "curator_id") id: String,
                                     @Path(value = "suggestion_id") suggestionId: String,
                                     @Body comment: Comment
    ): Single<Result<Comment>>

    @PUT("curators/{curator_id}/suggestions/{suggestion_id}/progress")
    fun updateCuratorProgress(@Path(value = "curator_id") curatorId: String,
                              @Path(value = "suggestion_id") suggestionId: String,
                              @Body progress: Progress?
    ): Single<Result<Progress>>


}