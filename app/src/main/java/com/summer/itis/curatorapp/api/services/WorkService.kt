package com.summer.itis.curatorapp.api.services

import com.summer.itis.curatorapp.model.comment.Comment
import com.summer.itis.curatorapp.model.step.Step
import com.summer.itis.curatorapp.model.theme.Theme
import com.summer.itis.curatorapp.model.work.Work
import io.reactivex.Single
import retrofit2.adapter.rxjava2.Result
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WorkService {

    @GET("works/{work_id}")
    fun findById(@Path(value = "work_id") id: String): Single<Result<Work>>

    @GET("works")
    fun findAll(): Single<Result<List<Work>>>

    @GET("works/{work_id}/steps")
    fun findWorkSteps(@Path(value = "work_id") curatorId: String): Single<Result<List<Step>>>

    @GET("works/{work_id}/steps/{step_id}")
    fun findWorkStep(@Path(value = "work_id") workId: String,
                     @Path(value = "step_id") stepId: String): Single<Result<Step>>

    @GET("works/{work_id}/steps/{step_id}/comments")
    fun findStepComments(@Path(value = "work_id") workId: String,
                         @Path(value = "step_id") stepId: String): Single<Result<List<Comment>>>

    @GET("works/{work_id}/steps/{step_id}/materials")
    fun findStepMaterials(@Path(value = "work_id") workId: String,
                          @Path(value = "step_id") stepId: String): Single<Result<List<String>>>

}