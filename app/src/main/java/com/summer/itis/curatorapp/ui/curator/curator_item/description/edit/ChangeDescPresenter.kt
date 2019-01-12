package com.summer.itis.curatorapp.ui.curator.curator_item.description.edit

import com.arellomobile.mvp.InjectViewState
import com.summer.itis.curatorapp.R
import com.summer.itis.curatorapp.model.user.Curator
import com.summer.itis.curatorapp.repository.RepositoryProvider
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragPresenter
import com.summer.itis.curatorapp.utils.AppHelper
import io.reactivex.disposables.CompositeDisposable

@InjectViewState
class ChangeDescPresenter(): BaseFragPresenter<ChangeDescView>() {

    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun saveCuratorDesc(curator: Curator) {
        viewState.startTimeout (R.string.failed_save_curator_desc)
        val disposable = RepositoryProvider.curatorRepository.update(curator.id, curator).subscribe { res ->
            interceptSecondResponse(res, saveChanges(),
                R.string.failed_save_curator_desc)
        }
        compositeDisposable.add(disposable)
    }

    private fun saveChanges(): (curator: Curator) -> Unit {
        return {
            viewState.stopTimeout()
            AppHelper.currentCurator.description = it.description
            viewState.showChanges()
        }
    }

}