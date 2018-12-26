package com.summer.itis.curatorapp.api.services

import com.summer.itis.curatorapp.model.theme.Theme
import io.reactivex.Single
import retrofit2.adapter.rxjava2.Result
import retrofit2.http.*

interface ThemeService {

    @GET("themes/{theme_id}")
    fun findById(@Path(value = "theme_id") id: String): Single<Result<Theme>>

    @GET("themes")
    fun findAll(): Single<Result<List<Theme>>>

    @GET("curators/{curator_id}/themes")
    fun findCuratorThemes(@Path(value = "curator_id") curatorId: String): Single<Result<List<Theme>>>

    @GET("curators/{curator_id}/themes/{theme_id}")
    fun findCuratorTheme(@Path(value = "curator_id") curatorId: String, @Path(value = "theme_id") themeId: String): Single<Result<Theme>>

    @POST("curators/{curator_id}/themes")
    fun postCuratorTheme(@Path(value = "curator_id") id: String,
                         @Body theme: Theme
    ): Single<Result<Theme>>

    @DELETE("curators/{curator_id}/themes/{theme_id}")
    fun deleteCuratorTheme(@Path(value = "curator_id") curatorId: String,
                           @Path(value = "theme_id") themeId: String): Single<Result<Theme>>

    @PUT("curators/{curator_id}/themes/{theme_id}")
    fun updateCuratorTheme(@Path(value = "curator_id") curatorId: String,
                           @Path(value = "theme_id") themeId: String,
                           @Body theme: Theme
    ): Single<Result<Theme>>

}