package com.summer.itis.curatorapp.repository.work

import com.summer.itis.curatorapp.api.services.WorkService
import com.summer.itis.curatorapp.model.work.Work
import com.summer.itis.curatorapp.repository.base.BaseRepositoryImpl
import com.summer.itis.curatorapp.utils.RxUtils
import io.reactivex.Single
import retrofit2.adapter.rxjava2.Result

class WorkRepositoryImpl(val apiService: WorkService): WorkRepository {

    override fun findById(workId: String): Single<Result<Work>> {
        return apiService.findById(workId).compose(RxUtils.asyncSingle())
    }

    override fun findCuratorWorks(curatorId: String): Single<Result<List<Work>>> {
        return apiService.findCuratorWorks(curatorId).compose(RxUtils.asyncSingle())
    }

    override fun findCuratorWork(curatorId: String, workId: String): Single<Result<Work>> {
        return apiService.findCuratorWork(curatorId, workId).compose(RxUtils.asyncSingle())
    }

    override fun postCuratorWork(id: String, work: Work): Single<Result<Work>> {
        return apiService.postCuratorWork(id, work).compose(RxUtils.asyncSingle())
    }

    override fun updateCuratorWork(curatorId: String, work: Work): Single<Result<Work>> {
        return apiService.updateCuratorWork(curatorId, work.id, work).compose(RxUtils.asyncSingle())
    }

    override fun deleteCuratorWork(curatorId: String, workId: String): Single<Result<Work>> {
        return apiService.deleteCuratorWork(curatorId, workId).compose(RxUtils.asyncSingle())
    }

}