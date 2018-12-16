package com.summer.itis.curatorapp.api.services

import com.summer.itis.curatorapp.model.comment.Comment
import com.summer.itis.curatorapp.model.skill.Skill
import com.summer.itis.curatorapp.model.step.Step
import com.summer.itis.curatorapp.model.theme.SuggestionTheme
import com.summer.itis.curatorapp.model.theme.Theme
import com.summer.itis.curatorapp.model.user.Curator
import com.summer.itis.curatorapp.model.work.Work
import io.reactivex.Single
import retrofit2.adapter.rxjava2.Result
import retrofit2.http.*

interface CuratorService {

    @GET("curators/{curator_id}")
    fun findById(@Path(value = "curator_id") id: String): Single<Result<Curator>>

    @GET("curators")
    fun findAll(): Single<Result<List<Curator>>>

    @GET("curators/{curator_id}/skills")
    fun findSkills(@Path(value = "curator_id") id: String): Single<Result<List<Skill>>>

    @GET("curators/{curator_id}/suggestions")
    fun findSuggestions(@Path(value = "curator_id") id: String): Single<Result<List<SuggestionTheme>>>

    @GET("curators/{curator_id}/suggestions/{suggestion_id}")
    fun findSuggestion(@Path(value = "curator_id") curatorId: String, @Path(value = "suggestion_id") suggestionId: String): Single<Result<SuggestionTheme>>

    @GET("curators/{curator_id}/suggestions/{suggestion_id}/comments")
    fun findSuggestionComments(@Path(value = "curator_id") curatorId: String, @Path(value = "suggestion_id") suggestionId: String): Single<Result<List<Comment>>>

    @GET("curators/{curator_id}/themes")
    fun findThemes(@Path(value = "curator_id") curatorId: String): Single<Result<List<Theme>>>

    @GET("curators/{curator_id}/themes/{theme_id}")
    fun findTheme(@Path(value = "curator_id") curatorId: String, @Path(value = "theme_id") themeId: String): Single<Result<Theme>>

    @GET("curators/{curator_id}/works")
    fun findWorks(@Path(value = "curator_id") curatorId: String): Single<Result<List<Work>>>

    @GET("curators/{curator_id}/works/{work_id}")
    fun findWork(@Path(value = "curator_id") curatorId: String, @Path(value = "work_id") workId: String): Single<Result<Work>>

    @GET("curators/{curator_id}/works/{work_id}/steps")
    fun findWorkSteps(@Path(value = "curator_id") curatorId: String,
                      @Path(value = "work_id") workId: String): Single<Result<List<Step>>>

    @GET("curators/{curator_id}/works/{work_id}/steps/{step_id}")
    fun findWorkStep(@Path(value = "curator_id") curatorId: String,
                     @Path(value = "work_id") workId: String,
                     @Path(value = "step_id") stepId: String): Single<Result<Step>>

    @GET("curators/{curator_id}/works/{work_id}/steps/{step_id}/comments")
    fun findStepComments(@Path(value = "curator_id") curatorId: String,
                         @Path(value = "work_id") workId: String,
                         @Path(value = "step_id") stepId: String): Single<Result<List<Comment>>>

    @GET("curators/{curator_id}/works/{work_id}/steps/{step_id}/materials")
    fun findStepMaterials(@Path(value = "curator_id") curatorId: String,
                         @Path(value = "work_id") workId: String,
                         @Path(value = "step_id") stepId: String): Single<Result<List<String>>>



    @PUT("curators/{curator_id}")
    fun update(@Path(value = "curator_id") id: String,
               @Body curator: Curator): Single<Result<Curator>>

    @POST("curators/{curator_id}/suggestions")
    fun postSuggestion(@Path(value = "curator_id") id: String,
                       @Body suggestionTheme: SuggestionTheme): Single<Result<SuggestionTheme>>

    @DELETE("curators/{curator_id}/suggestions/{suggestion_id}")
    fun deleteSuggestion(@Path(value = "curator_id") curatorId: String,
                         @Path(value = "suggestion_id") suggestionId: String): Single<Result<SuggestionTheme>>

    @PUT("curators/{curator_id}/suggestions/{suggestion_id}")
    fun updateSuggestion(@Path(value = "curator_id") curatorId: String,
                         @Path(value = "suggestion_id") suggestionId: String,
                         @Body suggestionTheme: SuggestionTheme): Single<Result<SuggestionTheme>>

    @POST("curators/{curator_id}/suggestions/{suggestion_id}/comments")
    fun postSuggestionComment(@Path(value = "curator_id") id: String,
                              @Path(value = "suggestion_id") suggestionId: String,
                              @Body comment: Comment): Single<Result<Comment>>

    @POST("curators/{curator_id}/themes")
    fun postTheme(@Path(value = "curator_id") id: String,
                  @Body theme: Theme): Single<Result<Theme>>

    @DELETE("curators/{curator_id}/themes/{theme_id}")
    fun deleteTheme(@Path(value = "curator_id") curatorId: String,
                    @Path(value = "theme_id") themeId: String): Single<Result<Theme>>

    @PUT("curators/{curator_id}/themes/{theme_id}")
    fun updateTheme(@Path(value = "curator_id") curatorId: String,
                    @Path(value = "theme_id") themeId: String,
                    @Body theme: Theme): Single<Result<Theme>>


    @POST("curators/{curator_id}/works")
    fun postWork(@Path(value = "curator_id") id: String,
                  @Body work: Work): Single<Result<Work>>

    @DELETE("curators/{curator_id}/works/{work_id}")
    fun deleteWork(@Path(value = "curator_id") curatorId: String,
                   @Path(value = "work_id") workId: String): Single<Result<Work>>

    @PUT("curators/{curator_id}/works/{work_id}")
    fun updateWork(@Path(value = "curator_id") curatorId: String,
                   @Path(value = "work_id") workId: String,
                   @Body work: Work): Single<Result<Work>>

    @POST("curators/{curator_id}/works/{work_id}/steps")
    fun postWorkStep(@Path(value = "curator_id") id: String,
                     @Path(value = "work_id") workId: String,
                     @Body step: Step): Single<Result<Step>>

    @DELETE("curators/{curator_id}/works/{work_id}/steps/{step_id}")
    fun deleteWorkStep(@Path(value = "curator_id") curatorId: String,
                       @Path(value = "work_id") workId: String,
                       @Path(value = "step_id") stepId: String): Single<Result<Step>>

    @PUT("curators/{curator_id}/works/{work_id}/steps/{step_id}")
    fun updateWorkStep(@Path(value = "curator_id") curatorId: String,
                       @Path(value = "work_id") workId: String,
                       @Path(value = "step_id") stepId: String,
                       @Body step: Step): Single<Result<Step>>

    @POST("curators/{curator_id}/works/{work_id}/steps/{step_id}/comments")
    fun postWorkStepComment(@Path(value = "curator_id") id: String,
                            @Path(value = "work_id") workId: String,
                            @Path(value = "step_id") stepId: String,
                            @Body comment: Comment): Single<Result<Comment>>

    @POST("curators/{curator_id}/works/{work_id}/steps/{step_id}/materials")
    fun postWorkStepMaterial(@Path(value = "curator_id") id: String,
                             @Path(value = "work_id") workId: String,
                             @Path(value = "step_id") stepId: String,
                             @Body link: String): Single<Result<String>>


}
