package com.summer.itis.curatorapp.api.services

import com.summer.itis.curatorapp.model.skill.Skill
import com.summer.itis.curatorapp.model.theme.Theme
import io.reactivex.Single
import retrofit2.adapter.rxjava2.Result
import retrofit2.http.GET
import retrofit2.http.Path

interface ThemeService {

    @GET("themes/{theme_id}")
    fun findById(@Path(value = "theme_id") id: String): Single<Result<Theme>>

    @GET("themes")
    fun findAll(): Single<Result<List<Theme>>>

}