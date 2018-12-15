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


}
