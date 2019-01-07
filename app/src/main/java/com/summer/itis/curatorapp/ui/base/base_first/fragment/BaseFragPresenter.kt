package com.summer.itis.curatorapp.ui.base.base_first.fragment

import android.util.Log
import com.arellomobile.mvp.MvpPresenter
import com.summer.itis.curatorapp.utils.AppHelper
import com.summer.itis.curatorapp.utils.Const
import retrofit2.adapter.rxjava2.Result

open class BaseFragPresenter<View: BaseFragView>: MvpPresenter<View>() {

    protected fun <T> interceptResponse(res: Result<T>?, acceptBody: (body: T) -> Unit) {
        Log.d(Const.TAG_LOG, "received result")
        if(res == null) {
            Log.d(Const.TAG_LOG, "result == null")
        } else {
            if(res.response() == null) {
                Log.d(Const.TAG_LOG, "response == null and isError = ${res.isError}")
                Log.d(Const.TAG_LOG, "error = ${res.error()?.message}")
                res.error()?.printStackTrace()
            }
        }
        res?.response()?.let {
            if (it.isSuccessful) {
                Log.d(Const.TAG_LOG, "successful response")
                it.body()?.let { students ->
                    acceptBody(students)
                  /*  AppHelper.currentCurator.description = curator.description
                    viewState.saveCuratorState()
                    viewState.showChanges()*/
                }
            } else {
                Log.d(Const.TAG_LOG, "failed response")
                Log.d(Const.TAG_LOG, "mes = ${it.message()}")

            }
        }
    }
}