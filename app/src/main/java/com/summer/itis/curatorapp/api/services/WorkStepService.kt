package com.summer.itis.curatorapp.api.services

import com.summer.itis.curatorapp.model.comment.Comment
import com.summer.itis.curatorapp.model.step.Step
import io.reactivex.Single
import retrofit2.adapter.rxjava2.Result
import retrofit2.http.*

interface WorkStepService {

    @GET("works/{work_id}/steps")
    fun findAll(@Path(value = "work_id") curatorId: String): Single<Result<List<Step>>>

    @GET("works/{work_id}/steps/{step_id}")
    fun findById(@Path(value = "work_id") workId: String,
                     @Path(value = "step_id") stepId: String): Single<Result<Step>>

    @GET("works/{work_id}/steps/{step_id}/comments")
    fun findStepComments(@Path(value = "work_id") workId: String,
                         @Path(value = "step_id") stepId: String): Single<Result<List<Comment>>>

    @GET("works/{work_id}/steps/{step_id}/materials")
    fun findStepMaterials(@Path(value = "work_id") workId: String,
                          @Path(value = "step_id") stepId: String): Single<Result<List<String>>>

    @GET("curators/{curator_id}/works/{work_id}/steps")
    fun findCuratorWorkSteps(@Path(value = "curator_id") curatorId: String,
                             @Path(value = "work_id") workId: String): Single<Result<List<Step>>>

    @GET("curators/{curator_id}/works/{work_id}/steps/{step_id}")
    fun findCuratorWorkStep(@Path(value = "curator_id") curatorId: String,
                            @Path(value = "work_id") workId: String,
                            @Path(value = "step_id") stepId: String): Single<Result<Step>>

    @GET("curators/{curator_id}/works/{work_id}/steps/{step_id}/comments")
    fun findCuratorStepComments(@Path(value = "curator_id") curatorId: String,
                                @Path(value = "work_id") workId: String,
                                @Path(value = "step_id") stepId: String): Single<Result<List<Comment>>>

    @GET("curators/{curator_id}/works/{work_id}/steps/{step_id}/materials")
    fun findCuratorStepMaterials(@Path(value = "curator_id") curatorId: String,
                                 @Path(value = "work_id") workId: String,
                                 @Path(value = "step_id") stepId: String): Single<Result<List<String>>>


    @POST("curators/{curator_id}/works/{work_id}/steps")
    fun postCuratorWorkStep(@Path(value = "curator_id") id: String,
                            @Path(value = "work_id") workId: String,
                            @Body step: Step
    ): Single<Result<Step>>

    @DELETE("curators/{curator_id}/works/{work_id}/steps/{step_id}")
    fun deleteCuratorWorkStep(@Path(value = "curator_id") curatorId: String,
                              @Path(value = "work_id") workId: String,
                              @Path(value = "step_id") stepId: String): Single<Result<Step>>

    @PUT("curators/{curator_id}/works/{work_id}/steps/{step_id}")
    fun updateCuratorWorkStep(@Path(value = "curator_id") curatorId: String,
                              @Path(value = "work_id") workId: String,
                              @Path(value = "step_id") stepId: String,
                              @Body step: Step
    ): Single<Result<Step>>

    @POST("curators/{curator_id}/works/{work_id}/steps/{step_id}/comments")
    fun postCuratorWorkStepComment(@Path(value = "curator_id") id: String,
                                   @Path(value = "work_id") workId: String,
                                   @Path(value = "step_id") stepId: String,
                                   @Body comment: Comment): Single<Result<Comment>>

    @POST("curators/{curator_id}/works/{work_id}/steps/{step_id}/materials")
    fun postCuratorWorkStepMaterial(@Path(value = "curator_id") id: String,
                                    @Path(value = "work_id") workId: String,
                                    @Path(value = "step_id") stepId: String,
                                    @Body link: String): Single<Result<String>>

}