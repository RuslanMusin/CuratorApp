package com.summer.itis.curatorapp.ui.curator.curator_item.view

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.summer.itis.curatorapp.model.user.Curator
import com.summer.itis.curatorapp.repository.RepositoryProvider
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragPresenter
import com.summer.itis.curatorapp.utils.AppHelper
import com.summer.itis.curatorapp.utils.Const.OWNER_TYPE
import com.summer.itis.curatorapp.utils.Const.TAG_LOG
import com.summer.itis.curatorapp.utils.Const.WATCHER_TYPE
import io.reactivex.disposables.CompositeDisposable

@InjectViewState
class CuratorPresenter(): BaseFragPresenter<CuratorView>() {

    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun logout() {
        val disposable = RepositoryProvider.authRepository.logout().subscribe { res ->
            if(res.response()?.code() == 200) {
                viewState.logout()
            } else {
                interceptResponse(res) { Log.d(TAG_LOG, "intercept")}
            }
        }
        compositeDisposable.add(disposable)
    }

    fun findCurator(curatorId: String) {
        val disposable = RepositoryProvider.curatorRepository.findById(curatorId).subscribe { res ->
            interceptResponse(res, handleCurator())
        }
        compositeDisposable.add(disposable)
    }

    private fun handleCurator(): (curator: Curator) -> Unit {
        return {
            var type = WATCHER_TYPE
            if (AppHelper.currentCurator.id.equals(it.id)) {
                type = OWNER_TYPE
            }
            viewState.initViews(it, type)
        }
    }


}
