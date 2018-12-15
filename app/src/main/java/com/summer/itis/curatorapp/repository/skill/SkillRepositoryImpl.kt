package com.summer.itis.curatorapp.repository.skill

import com.summer.itis.curatorapp.api.services.SkillService
import com.summer.itis.curatorapp.model.skill.Skill
import com.summer.itis.curatorapp.repository.base.BaseRepositoryImpl
import io.reactivex.Single

class SkillRepositoryImpl(override val apiService: SkillService) :
        SkillRepository, BaseRepositoryImpl<SkillService, Skill>() {
    override fun findMySkills(id: String): Single<List<Skill>> {
        return apiService.findMySkills(id)
    }

}