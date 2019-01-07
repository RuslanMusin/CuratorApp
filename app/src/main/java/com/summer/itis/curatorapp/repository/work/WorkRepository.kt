package com.summer.itis.curatorapp.repository.work

import com.summer.itis.curatorapp.model.work.Work
import io.reactivex.Single
import retrofit2.adapter.rxjava2.Result

interface WorkRepository {

    fun findById(workId: String): Single<Result<Work>>
    fun findCuratorWorks(id: String): Single<Result<List<Work>>>
    fun findCuratorWork(curatorId: String, workId: String): Single<Result<Work>>

    fun postCuratorWork(id: String, work: Work): Single<Result<Work>>
    fun updateCuratorWork(curatorId: String, work: Work): Single<Result<Work>>
    fun deleteCuratorWork(curatorId: String, workId: String): Single<Result<Work>>


}