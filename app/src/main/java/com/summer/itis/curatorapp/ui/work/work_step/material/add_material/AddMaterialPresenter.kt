package com.summer.itis.curatorapp.ui.work.work_step.material.add_material

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.summer.itis.curatorapp.model.material.Material
import com.summer.itis.curatorapp.repository.RepositoryProvider
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragPresenter
import com.summer.itis.curatorapp.utils.Const
import io.reactivex.disposables.CompositeDisposable

@InjectViewState
class AddMaterialPresenter(): BaseFragPresenter<AddMaterialView>() {

    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun postMaterial(curatorId: String, workId: String, stepId: String, material: Material) {
        val disposable = RepositoryProvider.materialRepository
            .postMaterial(
                curatorId,
                workId,
                stepId,
                material
            ).subscribe { res ->
                interceptResponse(res) { viewState.showChanges(it) }
        }
        compositeDisposable.add(disposable)

    }
}