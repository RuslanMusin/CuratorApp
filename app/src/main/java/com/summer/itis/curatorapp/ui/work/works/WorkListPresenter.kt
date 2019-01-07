package com.summer.itis.curatorapp.ui.work.works

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.summer.itis.curatorapp.repository.RepositoryProvider
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragPresenter
import com.summer.itis.curatorapp.utils.Const
import io.reactivex.disposables.CompositeDisposable

@InjectViewState
class WorkListPresenter(): BaseFragPresenter<WorkListView>() {

    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun loadWorks(userId: String) {
        Log.d(Const.TAG_LOG, "id = $userId")
        val disposable = RepositoryProvider.worksRepository.findCuratorWorks(userId).subscribe { res ->
            Log.d(Const.TAG_LOG, "receive work response")
            if(res == null) {
                Log.d(Const.TAG_LOG, "work res == null")
            } else {
                if(res.response() == null) {
                    Log.d(Const.TAG_LOG, "work response == null and isError = ${res.isError}")
                    Log.d(Const.TAG_LOG, "work error = ${res.error()?.message}")
                    res.error()?.printStackTrace()
                }
            }
            res?.response()?.let {
                if (it.isSuccessful) {
                    Log.d(Const.TAG_LOG, "successful works")
                    it.body()?.let { works ->
                        viewState.showWorks(works)
                    }
                } else {
                    Log.d(Const.TAG_LOG, "failed works")

                }
            }
        }
        compositeDisposable.add(disposable)
    }

}