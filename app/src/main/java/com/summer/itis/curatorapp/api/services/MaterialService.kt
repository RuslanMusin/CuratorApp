package com.summer.itis.curatorapp.api.services

import com.summer.itis.curatorapp.model.material.Material
import io.reactivex.Single
import retrofit2.adapter.rxjava2.Result
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface MaterialService {

    @GET("works/{work_id}/steps/{step_id}/materials")
    fun findAll(
        @Path(value = "work_id") workId: String,
        @Path(value = "step_id") stepId: String
    ): Single<Result<List<Material>>>

    @POST("curators/{curator_id}/works/{work_id}/steps/{step_id}/materials")
    fun postMaterial(
        @Path(value = "curator_id") curatorId: String,
        @Path(value = "work_id") workId: String,
        @Path(value = "step_id") stepId: String,
        @Body material: Material
    ): Single<Result<Material>>

}