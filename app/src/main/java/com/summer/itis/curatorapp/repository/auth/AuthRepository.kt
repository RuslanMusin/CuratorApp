package com.summer.itis.curatorapp.repository.auth



import com.summer.itis.curatorapp.model.api_result.LoginBody
import com.summer.itis.curatorapp.model.api_result.LoginResult
import com.summer.itis.curatorapp.model.user.User
import io.reactivex.Single
import retrofit2.adapter.rxjava2.Result

interface AuthRepository {

    fun login(user: User): Single<Result<LoginResult>>
    fun logout(): Single<Result<Unit>>
}
