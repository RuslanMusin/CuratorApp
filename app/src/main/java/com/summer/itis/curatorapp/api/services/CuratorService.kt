package com.summer.itis.curatorapp.api.services

import com.summer.itis.curatorapp.model.user.Curator
import io.reactivex.Single
import retrofit2.adapter.rxjava2.Result
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface CuratorService {

    @GET("curators/{curator_id}")
    fun findById(
        @Path(value = "curator_id") id: String
    ): Single<Result<Curator>>

    @GET("curators")
    fun findAll(): Single<Result<List<Curator>>>

    @PUT("curators/{curator_id}")
    fun update(
        @Path(value = "curator_id") id: String,
        @Body curator: Curator
    ): Single<Result<Curator>>

}
