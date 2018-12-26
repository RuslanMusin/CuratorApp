package com.summer.itis.curatorapp.repository.material

import com.summer.itis.curatorapp.api.services.MaterialService
import com.summer.itis.curatorapp.model.material.Material
import com.summer.itis.curatorapp.utils.RxUtils
import io.reactivex.Single
import retrofit2.adapter.rxjava2.Result

class MaterialRepositoryImpl(val apiService: MaterialService): MaterialRepository {

    override fun findAll(workId: String, stepId: String): Single<Result<List<Material>>> {
        return apiService.findAll(workId, stepId).compose(RxUtils.asyncSingle())
    }

    override fun postMaterial(
        curatorId: String,
        workId: String,
        stepId: String,
        material: Material
    ): Single<Result<Material>> {
        return apiService.postMaterial(curatorId, workId, stepId, material).compose(RxUtils.asyncSingle())
    }

}