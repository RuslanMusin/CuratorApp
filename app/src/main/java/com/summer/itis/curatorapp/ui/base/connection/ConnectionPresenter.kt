package com.summer.itis.curatorapp.ui.base.connection

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.summer.itis.curatorapp.repository.RepositoryProvider
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragPresenter
import com.summer.itis.curatorapp.ui.work.work_item.WorkView
import com.summer.itis.curatorapp.utils.Const
import io.reactivex.disposables.CompositeDisposable

@InjectViewState
class  ConnectionPresenter(): BaseFragPresenter<ConnectionView>() {

    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun loadWork(workId: String) {
        val disposable = RepositoryProvider.worksRepository.findById(workId).subscribe { res ->
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
                    Log.d(Const.TAG_LOG, "successful work")
                    it.body()?.let { work ->
                    }
                } else {
                    Log.d(Const.TAG_LOG, "failed work")

                }
            }
        }
        compositeDisposable.add(disposable)
    }

}