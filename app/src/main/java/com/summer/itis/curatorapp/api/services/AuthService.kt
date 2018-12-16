package com.summer.itis.curatorapp.api.services

import com.summer.itis.curatorapp.model.api_result.LoginBody
import com.summer.itis.curatorapp.model.api_result.LoginResult
import io.reactivex.Single
import retrofit2.adapter.rxjava2.Result
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("login")
    fun login(@Body loginBody: LoginBody): Single<Result<LoginResult>>

    @POST("logout")
    fun logout(): Single<Result<Unit>>
}