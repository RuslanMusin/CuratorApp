package com.summer.itis.curatorapp.repository.theme

import com.summer.itis.curatorapp.model.theme.Theme
import com.summer.itis.curatorapp.model.help.ThemeApi
import io.reactivex.Single
import retrofit2.adapter.rxjava2.Result

interface ThemeRepository {
    fun findCuratorThemes(id: String): Single<Result<List<Theme>>>
    fun findCuratorTheme(curatorId: String, themeId: String): Single<Result<Theme>>

    fun postCuratorTheme(id: String, theme: ThemeApi): Single<Result<Theme>>
    fun updateCuratorTheme(curatorId: String, theme: ThemeApi): Single<Result<Theme>>
    fun deleteCuratorTheme(curatorId: String, themeId: String): Single<Result<Theme>>
}