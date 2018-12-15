package com.summer.itis.curatorapp.repository.common

import com.summer.itis.curatorapp.api.ApiFactory.Companion.commonService
import com.summer.itis.curatorapp.model.api_result.LoginBody
import com.summer.itis.curatorapp.model.api_result.LoginResult
import com.summer.itis.curatorapp.utils.RxUtils



import io.reactivex.Single
import retrofit2.adapter.rxjava2.Result


class CommonRepositoryImpl : CommonRepository {

    override fun login(loginBody: LoginBody): Single<Result<LoginResult>> {
        return commonService
            .login(loginBody)
            .compose(RxUtils.asyncSingle())
    }
}
