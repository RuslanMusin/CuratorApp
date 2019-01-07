package com.summer.itis.curatorapp.repository.subject

import com.summer.itis.curatorapp.model.skill.Subject
import io.reactivex.Single
import retrofit2.adapter.rxjava2.Result

interface SubjectRepository {

    fun findById(subjectId: String): Single<Result<Subject>>

    fun findAll(): Single<Result<List<Subject>>>

}