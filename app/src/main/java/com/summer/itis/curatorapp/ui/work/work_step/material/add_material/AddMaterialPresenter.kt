package com.summer.itis.curatorapp.ui.work.work_step.material.add_material

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.summer.itis.curatorapp.model.material.Material
import com.summer.itis.curatorapp.model.user.Curator
import com.summer.itis.curatorapp.repository.RepositoryProvider
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragPresenter
import com.summer.itis.curatorapp.ui.curator.curator_item.description.edit.ChangeDescView
import com.summer.itis.curatorapp.utils.AppHelper
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
            Log.d(Const.TAG_LOG, "receive subjects response")
            if(res == null) {
                Log.d(Const.TAG_LOG, "res == null")
            } else {
                if(res.response() == null) {
                    Log.d(Const.TAG_LOG, "response == null and isError = ${res.isError}")
                    Log.d(Const.TAG_LOG, "error = ${res.error()?.message}")
                    res.error()?.printStackTrace()
                }
            }
            res?.response()?.let {
                if (it.isSuccessful) {
                    Log.d(Const.TAG_LOG, "successful subjects")
                    it.body()?.let { material ->
                        viewState.showChanges(material)
                    }
                } else {
                    Log.d(Const.TAG_LOG, "failed desc changes")
                    Log.d(Const.TAG_LOG, "mes = ${it.message()}")

                }
            }
        }
        compositeDisposable.add(disposable)

    }
}