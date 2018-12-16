package com.summer.itis.curatorapp.repository.curator

import com.summer.itis.curatorapp.api.services.CuratorService
import com.summer.itis.curatorapp.model.help.CuratorApi
import com.summer.itis.curatorapp.model.user.Curator
import com.summer.itis.curatorapp.utils.RxUtils
import io.reactivex.Single
import retrofit2.adapter.rxjava2.Result

class CuratorRepositoryImpl(val apiService: CuratorService) :
        CuratorRepository {

    override lateinit var currentCurator: Curator

    override fun findById(id: String): Single<Result<Curator>> {
        return apiService.findById(id).compose(RxUtils.asyncSingle())
    }

    override fun findAll(): Single<Result<List<Curator>>> {
        return apiService.findAll().compose(RxUtils.asyncSingle())
    }

    override fun update(id: String, curator: CuratorApi): Single<Result<Curator>> {
        return apiService.update(id, curator).compose(RxUtils.asyncSingle())
    }

}