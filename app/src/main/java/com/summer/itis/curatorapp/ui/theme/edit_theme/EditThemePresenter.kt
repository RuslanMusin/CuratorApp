package com.summer.itis.curatorapp.ui.theme.edit_theme

import android.content.Intent
import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.summer.itis.curatorapp.model.theme.Theme
import com.summer.itis.curatorapp.repository.RepositoryProvider
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragPresenter
import com.summer.itis.curatorapp.utils.AppHelper
import com.summer.itis.curatorapp.utils.Const
import com.summer.itis.curatorapp.utils.Const.THEME_KEY
import com.summer.itis.curatorapp.utils.Const.gsonConverter
import io.reactivex.disposables.CompositeDisposable

@InjectViewState
class EditThemePresenter(): BaseFragPresenter<EditThemeView>() {

    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun updateTheme(theme: Theme) {
       theme.setApiFields()
        val disposable = RepositoryProvider.themeRepository
            .updateCuratorTheme(AppHelper.currentCurator.id, theme)
            .subscribe { res ->
                interceptResponse(res, handleUpdateTheme())
        }
        compositeDisposable.add(disposable)
    }

    private fun handleUpdateTheme(): (theme: Theme) -> Unit {
        return {
            AppHelper.currentCurator.themes.add(0, it)
            val intent = Intent()
            intent.putExtra(THEME_KEY , gsonConverter.toJson(it))
            viewState.returnEditResult(intent)
        }
    }

}