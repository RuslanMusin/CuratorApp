package com.summer.itis.curatorapp.api.services

import com.summer.itis.curatorapp.model.skill.Skill
import io.reactivex.Single
import retrofit2.adapter.rxjava2.Result
import retrofit2.http.GET
import retrofit2.http.Path

interface SkillService {

    @GET("skills/{skill_id}")
    fun findById(
        @Path(value = "skill_id") id: String
    ): Single<Result<Skill>>

    @GET("skills")
    fun findAll(): Single<Result<List<Skill>>>

    @GET("curators/{curator_id}/skills")
    fun findCuratorSkills(
        @Path(value = "curator_id") id: String
    ): Single<Result<List<Skill>>>

    @GET("students/{student_id}/skills")
    fun findStudentSkills(
        @Path(value = "student_id") id: String
    ): Single<Result<List<Skill>>>
}