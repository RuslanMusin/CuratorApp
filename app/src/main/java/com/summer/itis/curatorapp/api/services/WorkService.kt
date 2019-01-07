package com.summer.itis.curatorapp.api.services

import com.summer.itis.curatorapp.model.work.Work
import io.reactivex.Single
import retrofit2.adapter.rxjava2.Result
import retrofit2.http.*

interface WorkService {

    @GET("works/{work_id}")
    fun findById(@Path(value = "work_id") id: String): Single<Result<Work>>

    @GET("works")
    fun findAll(): Single<Result<List<Work>>>

    @GET("curators/{curator_id}/works")
    fun findCuratorWorks(@Path(value = "curator_id") curatorId: String): Single<Result<List<Work>>>

    @GET("curators/{curator_id}/works/{work_id}")
    fun findCuratorWork(@Path(value = "curator_id") curatorId: String, @Path(value = "work_id") workId: String): Single<Result<Work>>

    @POST("curators/{curator_id}/works")
    fun postCuratorWork(@Path(value = "curator_id") id: String,
                        @Body work: Work): Single<Result<Work>>

    @DELETE("curators/{curator_id}/works/{work_id}")
    fun deleteCuratorWork(@Path(value = "curator_id") curatorId: String,
                          @Path(value = "work_id") workId: String): Single<Result<Work>>

    @PUT("curators/{curator_id}/works/{work_id}")
    fun updateCuratorWork(@Path(value = "curator_id") curatorId: String,
                          @Path(value = "work_id") workId: String,
                          @Body work: Work): Single<Result<Work>>


}