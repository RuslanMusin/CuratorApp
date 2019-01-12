package com.summer.itis.curatorapp.api.services

import com.summer.itis.curatorapp.model.skill.Subject
import io.reactivex.Single
import retrofit2.adapter.rxjava2.Result
import retrofit2.http.GET
import retrofit2.http.Path

interface SubjectService {

    @GET("subjects/{subject_id}")
    fun findById(
        @Path(value = "subject_id") id: String
    ): Single<Result<Subject>>

    @GET("subjects")
    fun findAll(): Single<Result<List<Subject>>>

}