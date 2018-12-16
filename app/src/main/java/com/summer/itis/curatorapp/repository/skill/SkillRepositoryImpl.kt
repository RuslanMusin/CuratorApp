package com.summer.itis.curatorapp.repository.skill

import com.summer.itis.curatorapp.api.services.SkillService
import com.summer.itis.curatorapp.model.skill.Skill
import com.summer.itis.curatorapp.repository.base.BaseRepositoryImpl
import com.summer.itis.curatorapp.utils.RxUtils
import io.reactivex.Single
import retrofit2.adapter.rxjava2.Result

class SkillRepositoryImpl(val apiService: SkillService): SkillRepository {

    override fun findById(id: String): Single<Result<Skill>> {
        return apiService.findById(id).compose(RxUtils.asyncSingle())
    }

    override fun findAll(): Single<Result<List<Skill>>> {
        return apiService.findAll().compose(RxUtils.asyncSingle())
    }

    override fun findCuratorSkills(id: String): Single<Result<List<Skill>>> {
        return apiService.findCuratorSkills(id).compose(RxUtils.asyncSingle())
    }

    override fun findStudentSkills(id: String): Single<Result<List<Skill>>> {
        return apiService.findStudentSkills(id).compose(RxUtils.asyncSingle())
    }
}