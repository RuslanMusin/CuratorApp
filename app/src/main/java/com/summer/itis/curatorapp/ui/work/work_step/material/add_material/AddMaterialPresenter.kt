package com.summer.itis.curatorapp.ui.work.work_step.material.add_material

import com.arellomobile.mvp.InjectViewState
import com.summer.itis.curatorapp.R
import com.summer.itis.curatorapp.model.material.Material
import com.summer.itis.curatorapp.repository.RepositoryProvider
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragPresenter
import io.reactivex.disposables.CompositeDisposable

@InjectViewState
class AddMaterialPresenter(): BaseFragPresenter<AddMaterialView>() {

    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun postMaterial(curatorId: String, workId: String, stepId: String, material: Material) {
        viewState.startTimeout (R.string.failed_post_material)
        val disposable = RepositoryProvider.materialRepository
            .postMaterial(
                curatorId,
                workId,
                stepId,
                material
            ).subscribe { res ->
                interceptSecondResponse(res,
                    {
                        viewState.stopTimeout()
                        viewState.showChanges(it) },
                    R.string.failed_post_material
                    )
            }
        compositeDisposable.add(disposable)

    }
}