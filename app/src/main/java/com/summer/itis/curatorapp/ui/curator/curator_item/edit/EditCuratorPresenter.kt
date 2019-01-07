package com.summer.itis.curatorapp.ui.curator.curator_item.edit

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.summer.itis.curatorapp.model.user.Curator
import com.summer.itis.curatorapp.repository.RepositoryProvider
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragPresenter
import com.summer.itis.curatorapp.utils.Const
import io.reactivex.disposables.CompositeDisposable

@InjectViewState
class EditCuratorPresenter(): BaseFragPresenter<EditCuratorView>() {

    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun updateCurator(curator: Curator) {
        val disposable = RepositoryProvider.curatorRepository.update(curator.id, curator).subscribe { res ->
           interceptResponse(res) {
               viewState.returnAfterEdit()
           }
        }
        compositeDisposable.add(disposable)
    }

}