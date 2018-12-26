package com.summer.itis.curatorapp.ui.curator.curator_item.edit

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.summer.itis.curatorapp.model.help.CuratorApi
import com.summer.itis.curatorapp.model.user.Curator
import com.summer.itis.curatorapp.repository.RepositoryProvider
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragPresenter
import com.summer.itis.curatorapp.utils.Const
import io.reactivex.disposables.CompositeDisposable

@InjectViewState
class EditCuratorPresenter(): BaseFragPresenter<EditCuratorView>() {

    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun updateCurator(curator: Curator) {
//        val curatorApi = CuratorApi(curator)
        val disposable = RepositoryProvider.curatorRepository.update(curator.id, curator).subscribe { res ->
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
                    it.body()?.let { skills ->
                        viewState.returnAfterEdit()
                    }
                } else {
                    Log.d(Const.TAG_LOG, "failed profile changes")
                    Log.d(Const.TAG_LOG, "mes = ${it.message()}")

                }
            }
        }
        compositeDisposable.add(disposable)
    }

}