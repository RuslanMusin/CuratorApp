package com.summer.itis.curatorapp.repository.theme

import com.summer.itis.curatorapp.api.services.ThemeService
import com.summer.itis.curatorapp.model.theme.Theme
import com.summer.itis.curatorapp.utils.RxUtils
import io.reactivex.Single
import retrofit2.adapter.rxjava2.Result

class ThemeRepositoryImpl(val apiService: ThemeService): ThemeRepository {

    override fun findCuratorThemes(id: String): Single<Result<List<Theme>>> {
        return apiService.findCuratorThemes(id).compose(RxUtils.asyncSingle())
    }

    override fun findCuratorTheme(curatorId: String, themeId: String): Single<Result<Theme>> {
        return apiService.findCuratorTheme(curatorId, themeId).compose(RxUtils.asyncSingle())
    }

    override fun postCuratorTheme(id: String, theme: Theme): Single<Result<Theme>> {
        return apiService.postCuratorTheme(id, theme).compose(RxUtils.asyncSingle())
    }

    override fun updateCuratorTheme(curatorId: String, theme: Theme): Single<Result<Theme>> {
        return apiService.updateCuratorTheme(curatorId, theme.id, theme).compose(RxUtils.asyncSingle())
    }

    override fun deleteCuratorTheme(curatorId: String, themeId: String): Single<Result<Theme>> {
        return apiService.deleteCuratorTheme(curatorId, themeId).compose(RxUtils.asyncSingle())
    }
}