package com.summer.itis.curatorapp.ui.curator.curator_item.edit

import com.arellomobile.mvp.InjectViewState
import com.summer.itis.curatorapp.R
import com.summer.itis.curatorapp.model.user.Curator
import com.summer.itis.curatorapp.repository.RepositoryProvider
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragPresenter
import io.reactivex.disposables.CompositeDisposable

@InjectViewState
class EditCuratorPresenter(): BaseFragPresenter<EditCuratorView>() {

    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun updateCurator(curator: Curator) {
        viewState.startTimeout (R.string.failed_update_curator)
        val disposable = RepositoryProvider.curatorRepository.update(curator.id, curator).subscribe { res ->
           interceptSecondResponse(res, {
               viewState.stopTimeout()
               viewState.returnAfterEdit()
           },
               R.string.failed_update_curator)
        }
        compositeDisposable.add(disposable)
    }

}