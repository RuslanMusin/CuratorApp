package com.summer.itis.curatorapp.repository.skill

import com.summer.itis.curatorapp.model.skill.Skill
import io.reactivex.Single
import retrofit2.adapter.rxjava2.Result

interface SkillRepository {

    fun findById(studentId: String): Single<Result<Skill>>

    fun findAll(): Single<Result<List<Skill>>>

    fun findCuratorSkills(id: String): Single<Result<List<Skill>>>

    fun findStudentSkills(id: String): Single<Result<List<Skill>>>
}