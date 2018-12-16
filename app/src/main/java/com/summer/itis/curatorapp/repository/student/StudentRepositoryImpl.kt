package com.summer.itis.curatorapp.repository.student

import com.summer.itis.curatorapp.api.services.StudentService
import com.summer.itis.curatorapp.model.skill.Subject
import com.summer.itis.curatorapp.model.user.Student
import com.summer.itis.curatorapp.repository.base.BaseRepositoryImpl
import com.summer.itis.curatorapp.utils.RxUtils
import io.reactivex.Single
import retrofit2.adapter.rxjava2.Result

class StudentRepositoryImpl(val apiService: StudentService): StudentRepository {

    override fun findById(id: String): Single<Result<Student>> {
        return apiService.findById(id).compose(RxUtils.asyncSingle())
    }

    override fun findAll(): Single<Result<List<Student>>> {
        return apiService.findAll().compose(RxUtils.asyncSingle())
    }


}