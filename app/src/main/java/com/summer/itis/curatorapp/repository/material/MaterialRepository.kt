package com.summer.itis.curatorapp.repository.material

import com.summer.itis.curatorapp.model.material.Material
import io.reactivex.Single
import retrofit2.adapter.rxjava2.Result

interface MaterialRepository {

    fun findAll(workId: String, stepId: String): Single<Result<List<Material>>>

    fun postMaterial(
        curatorId: String,
        workId: String,
        stepId: String,
        material: Material
    ): Single<Result<Material>>

}