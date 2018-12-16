package com.summer.itis.curatorapp.repository.student

import com.summer.itis.curatorapp.model.user.Student
import com.summer.itis.curatorapp.repository.base.BaseRepository
import io.reactivex.Single
import retrofit2.adapter.rxjava2.Result

interface StudentRepository {

    fun findById(studentId: String): Single<Result<Student>>

    fun findAll(): Single<Result<List<Student>>>
}