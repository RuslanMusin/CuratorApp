package com.summer.itis.curatorapp.ui.work.work_step.material.list

import com.arellomobile.mvp.InjectViewState
import com.summer.itis.curatorapp.repository.RepositoryProvider
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragPresenter
import io.reactivex.disposables.CompositeDisposable

@InjectViewState
class MaterialListPresenter(): BaseFragPresenter<MaterialListView>() {

    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun loadMaterials(workId: String, stepId: String) {
        viewState.startTimeout { loadMaterials(workId, stepId) }
        val disposable = RepositoryProvider.materialRepository
            .findAll(workId, stepId)
            .subscribe { res ->
                interceptSecondResponse(res, {
                    viewState.stopTimeout()
                    viewState.showMaterials(it) },
                    { loadMaterials(workId, stepId) })
            }
        compositeDisposable.add(disposable)
    }

}