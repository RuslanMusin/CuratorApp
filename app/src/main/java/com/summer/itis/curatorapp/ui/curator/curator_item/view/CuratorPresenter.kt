package com.summer.itis.curatorapp.ui.curator.curator_item.view

import com.arellomobile.mvp.InjectViewState
import com.summer.itis.curatorapp.model.api_result.LoginBody
import com.summer.itis.curatorapp.model.user.Curator
import com.summer.itis.curatorapp.repository.RepositoryProvider
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragPresenter
import io.reactivex.disposables.CompositeDisposable

@InjectViewState
class CuratorPresenter(): BaseFragPresenter<CuratorView>() {

    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun logout() {
        val disposable = RepositoryProvider.commonRepository.logout().subscribe { res ->
            res?.response()?.let {
                if (it.isSuccessful) {
                    viewState.logout()
                } else {

                }
            }
        }
        compositeDisposable.add(disposable)
    }


}
