package com.summer.itis.curatorapp.repository.auth



import com.summer.itis.curatorapp.model.api_result.LoginBody
import com.summer.itis.curatorapp.model.api_result.LoginResult
import io.reactivex.Single
import retrofit2.adapter.rxjava2.Result

interface AuthRepository {

    fun login(loginBody: LoginBody): Single<Result<LoginResult>>
    fun logout(): Single<Result<Unit>>
}
