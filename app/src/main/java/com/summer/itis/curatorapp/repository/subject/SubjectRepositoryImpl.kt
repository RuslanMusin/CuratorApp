package com.summer.itis.curatorapp.repository.subject

import com.summer.itis.curatorapp.api.services.SubjectService
import com.summer.itis.curatorapp.model.skill.Subject
import com.summer.itis.curatorapp.utils.RxUtils
import io.reactivex.Single
import retrofit2.adapter.rxjava2.Result

class SubjectRepositoryImpl(val apiService: SubjectService): SubjectRepository {

    override fun findById(id: String): Single<Result<Subject>> {
        return apiService.findById(id).compose(RxUtils.asyncSingle())
    }

    override fun findAll(): Single<Result<List<Subject>>> {
        return apiService.findAll().compose(RxUtils.asyncSingle())
    }
}