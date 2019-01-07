package com.summer.itis.curatorapp.ui.curator.curator_item.description.edit

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.summer.itis.curatorapp.R.string.curator
import com.summer.itis.curatorapp.model.user.Curator
import com.summer.itis.curatorapp.repository.RepositoryProvider
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragPresenter
import com.summer.itis.curatorapp.utils.AppHelper
import com.summer.itis.curatorapp.utils.Const
import com.summer.itis.curatorapp.utils.Const.SUGGESTION_TYPE
import com.summer.itis.curatorapp.utils.Const.TAG_LOG
import com.summer.itis.curatorapp.utils.Const.THEME_TYPE
import io.reactivex.disposables.CompositeDisposable

@InjectViewState
class ChangeDescPresenter(): BaseFragPresenter<ChangeDescView>() {

    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun saveCuratorDesc(curator: Curator) {
        val disposable = RepositoryProvider.curatorRepository.update(curator.id, curator).subscribe { res ->
            interceptResponse(res, saveChanges())
        }
        compositeDisposable.add(disposable)
    }

    private fun saveChanges(): (curator: Curator) -> Unit {
        return {
            AppHelper.currentCurator.description = it.description
            viewState.showChanges()
        }
    }

}