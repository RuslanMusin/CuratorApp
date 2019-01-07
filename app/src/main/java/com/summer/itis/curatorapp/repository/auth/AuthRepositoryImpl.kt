package com.summer.itis.curatorapp.repository.auth

import com.summer.itis.curatorapp.api.ApiFactory.Companion.authService
import com.summer.itis.curatorapp.model.api_result.LoginBody
import com.summer.itis.curatorapp.model.api_result.LoginResult
import com.summer.itis.curatorapp.model.user.User
import com.summer.itis.curatorapp.utils.RxUtils



import io.reactivex.Single
import retrofit2.adapter.rxjava2.Result


class AuthRepositoryImpl : AuthRepository {

    override fun login(user: User): Single<Result<LoginResult>> {
        return authService
            .login(user)
            .compose(RxUtils.asyncSingle())
    }

    override fun logout(): Single<Result<Unit>> {
        return authService
            .logout()
            .compose(RxUtils.asyncSingle())
    }
}
