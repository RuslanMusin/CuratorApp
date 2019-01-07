package com.summer.itis.curatorapp.ui.curator.curator_item.description.edit

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.summer.itis.curatorapp.R.string.curator
import com.summer.itis.curatorapp.model.user.Curator
import com.summer.itis.curatorapp.repository.RepositoryProvider
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragPresenter
import com.summer.itis.curatorapp.utils.AppHelper
import com.summer.itis.curatorapp.utils.Const
import com.summer.itis.curatorapp.utils.Const.SUGGESTION_TYPE
import com.summer.itis.curatorapp.utils.Const.TAG_LOG
import com.summer.itis.curatorapp.utils.Const.THEME_TYPE
import io.reactivex.disposables.CompositeDisposable

@InjectViewState
class ChangeDescPresenter(): BaseFragPresenter<ChangeDescView>() {

    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun saveCuratorDesc(curator: Curator) {
        val disposable = RepositoryProvider.curatorRepository.update(curator.id, curator).subscribe { res ->
            interceptResponse(res, saveChanges())
           /* Log.d(Const.TAG_LOG, "receive subjects response")
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
                    it.body()?.let { students ->

                    }
                } else {
                    Log.d(Const.TAG_LOG, "failed desc changes")
                    Log.d(TAG_LOG, "mes = ${it.message()}")

                }
            }*/
        }
        compositeDisposable.add(disposable)
    }

    private fun saveChanges(): (curator: Curator) -> Unit {
        return {
            AppHelper.currentCurator.description = it.description
            viewState.showChanges()
        }
    }

}