package com.summer.itis.curatorapp.repository.curator

import com.summer.itis.curatorapp.model.user.Curator
import com.summer.itis.curatorapp.repository.base.BaseRepository
import io.reactivex.Single
import retrofit2.adapter.rxjava2.Result

interface CuratorRepository {

    var currentCurator: Curator

    //CRUD
    fun findById(id: String): Single<Result<Curator>>

}