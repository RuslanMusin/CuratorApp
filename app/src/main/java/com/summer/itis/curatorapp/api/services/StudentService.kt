package com.summer.itis.curatorapp.api.services

import com.summer.itis.curatorapp.model.comment.Comment
import com.summer.itis.curatorapp.model.skill.Skill
import com.summer.itis.curatorapp.model.step.Step
import com.summer.itis.curatorapp.model.theme.SuggestionTheme
import com.summer.itis.curatorapp.model.theme.Theme
import com.summer.itis.curatorapp.model.user.Curator
import com.summer.itis.curatorapp.model.user.Student
import com.summer.itis.curatorapp.model.work.Work
import io.reactivex.Single
import retrofit2.adapter.rxjava2.Result
import retrofit2.http.GET
import retrofit2.http.Path

interface StudentService {

    @GET("students/{student_id}")
    fun findById(@Path(value = "student_id") id: String): Single<Result<Student>>

    @GET("students")
    fun findAll(): Single<Result<List<Student>>>

    @GET("students/{student_id}/suggestions")
    fun findSuggestions(@Path(value = "student_id") id: String): Single<Result<List<SuggestionTheme>>>

    @GET("students/{student_id}/suggestions/{suggestion_id}")
    fun findSuggestion(@Path(value = "student_id") curatorId: String,
                       @Path(value = "suggestion_id") suggestionId: String): Single<Result<SuggestionTheme>>

    @GET("students/{student_id}/suggestions/{suggestion_id}/comments")
    fun findSuggestionComments(@Path(value = "student_id") curatorId: String,
                               @Path(value = "suggestion_id") suggestionId: String): Single<Result<List<Comment>>>

    @GET("students/{student_id}/themes")
    fun findThemes(@Path(value = "student_id") curatorId: String): Single<Result<List<Theme>>>

    @GET("students/{student_id}/themes/{theme_id}")
    fun findTheme(@Path(value = "student_id") curatorId: String,
                  @Path(value = "theme_id") themeId: String): Single<Result<Theme>>

    @GET("students/{student_id}/works")
    fun findWorks(@Path(value = "student_id") curatorId: String): Single<Result<List<Work>>>

    @GET("students/{student_id}/works/{work_id}")
    fun findWork(@Path(value = "student_id") curatorId: String,
                 @Path(value = "work_id") workId: String): Single<Result<Work>>

    @GET("students/{student_id}/works/{work_id}/steps")
    fun findWorkSteps(@Path(value = "student_id") curatorId: String,
                      @Path(value = "work_id") workId: String): Single<Result<List<Step>>>

    @GET("students/{student_id}/works/{work_id}/steps/{step_id}")
    fun findWorkStep(@Path(value = "student_id") curatorId: String,
                     @Path(value = "work_id") workId: String,
                     @Path(value = "step_id") stepId: String): Single<Result<Step>>

    @GET("students/{student_id}/works/{work_id}/steps/{step_id}/comments")
    fun findStepComments(@Path(value = "student_id") curatorId: String,
                         @Path(value = "work_id") workId: String,
                         @Path(value = "step_id") stepId: String): Single<Result<List<Comment>>>

    @GET("students/{student_id}/works/{work_id}/steps/{step_id}/materials")
    fun findStepMaterials(@Path(value = "student_id") curatorId: String,
                          @Path(value = "work_id") workId: String,
                          @Path(value = "step_id") stepId: String): Single<Result<List<String>>>

}