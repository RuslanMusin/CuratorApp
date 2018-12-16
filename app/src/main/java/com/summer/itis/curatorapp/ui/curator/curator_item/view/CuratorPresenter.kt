package com.summer.itis.curatorapp.ui.curator.curator_item.view

import com.arellomobile.mvp.InjectViewState
import com.summer.itis.curatorapp.repository.RepositoryProvider
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragPresenter
import com.summer.itis.curatorapp.utils.AppHelper
import com.summer.itis.curatorapp.utils.Const.OWNER_TYPE
import com.summer.itis.curatorapp.utils.Const.WATCHER_TYPE
import io.reactivex.disposables.CompositeDisposable

@InjectViewState
class CuratorPresenter(): BaseFragPresenter<CuratorView>() {

    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun logout() {
        val disposable = RepositoryProvider.authRepository.logout().subscribe { res ->
            res?.response()?.let {
                if (it.isSuccessful) {
                    viewState.logout()
                } else {

                }
            }
        }
        compositeDisposable.add(disposable)
    }

    fun findCurator(curatorId: String) {
        val disposable = RepositoryProvider.curatorRepository.findById(curatorId).subscribe { res ->
            res?.response()?.let {
                if (it.isSuccessful) {
                    val curator = it.body()
                    curator?.let {
                        var type = WATCHER_TYPE
                        if (AppHelper.currentCurator.id.equals(curator.id)) {
                            type = OWNER_TYPE
                        }
                        viewState.initViews(curator, type)
                    }
                } else {

                }
            }
        }
        compositeDisposable.add(disposable)
    }


}
