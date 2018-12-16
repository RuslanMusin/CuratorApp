package com.summer.itis.curatorapp.api.services

import com.summer.itis.curatorapp.model.comment.Comment
import com.summer.itis.curatorapp.model.theme.SuggestionTheme
import com.summer.itis.curatorapp.model.help.SuggestionApi
import io.reactivex.Single
import retrofit2.adapter.rxjava2.Result
import retrofit2.http.*

interface SuggestionService {

    @GET("curators/{curator_id}/suggestions")
    fun findCuratorSuggestions(@Path(value = "curator_id") id: String): Single<Result<List<SuggestionTheme>>>

    @GET("curators/{curator_id}/suggestions/{suggestion_id}")
    fun findCuratorSuggestion(@Path(value = "curator_id") curatorId: String,
                              @Path(value = "suggestion_id") suggestionId: String): Single<Result<SuggestionTheme>>

    @POST("curators/{curator_id}/suggestions")
    fun postCuratorSuggestion(@Path(value = "curator_id") id: String,
                              @Body suggestionTheme: SuggestionApi
    ): Single<Result<SuggestionTheme>>

    @GET("curators/{curator_id}/suggestions/{suggestion_id}/comments")
    fun findCuratorSuggestionComments(@Path(value = "curator_id") curatorId: String,
                                      @Path(value = "suggestion_id") suggestionId: String): Single<Result<List<Comment>>>

    @DELETE("curators/{curator_id}/suggestions/{suggestion_id}")
    fun deleteCuratorSuggestion(@Path(value = "curator_id") curatorId: String,
                                @Path(value = "suggestion_id") suggestionId: String): Single<Result<SuggestionTheme>>

    @PUT("curators/{curator_id}/suggestions/{suggestion_id}")
    fun updateCuratorSuggestion(@Path(value = "curator_id") curatorId: String,
                                @Path(value = "suggestion_id") suggestionId: String,
                                @Body suggestionTheme: SuggestionApi
    ): Single<Result<SuggestionTheme>>

    @POST("curators/{curator_id}/suggestions/{suggestion_id}/comments")
    fun postCuratorSuggestionComment(@Path(value = "curator_id") id: String,
                                     @Path(value = "suggestion_id") suggestionId: String,
                                     @Body comment: Comment
    ): Single<Result<Comment>>

}