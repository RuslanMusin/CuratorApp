package com.summer.itis.curatorapp.ui.base.base_first.fragment

import android.util.Log
import com.arellomobile.mvp.MvpPresenter
import com.summer.itis.curatorapp.utils.Const
import retrofit2.adapter.rxjava2.Result

open class BaseFragPresenter<View: BaseFragView>: MvpPresenter<View>() {

    protected fun <T> interceptSecondResponse(
        res: Result<T>?,
        acceptBody: (body: T) -> Unit,
        errorMessage: Int
    ) {
        Log.d(Const.TAG_LOG, "received result")
        if(res == null) {
            Log.d(Const.TAG_LOG, "result == null")
        } else {
            if(res.response() == null) {
                Log.d(Const.TAG_LOG, "response == null and isError = ${res.isError}")
                Log.d(Const.TAG_LOG, "error = ${res.error()?.message}")
                res.error()?.printStackTrace()
                viewState.showConnectionError(errorMessage)
            }
        }
        res?.response()?.let {
            if (it.isSuccessful) {
                Log.d(Const.TAG_LOG, "successful response")
                it.body()?.let { students ->
                    acceptBody(students)
                }
            } else {
                Log.d(Const.TAG_LOG, "failed response")
                Log.d(Const.TAG_LOG, "mes = ${it.message()}")
                viewState.showConnectionError(errorMessage)

            }
        }
    }

    protected fun <T> interceptSecondResponse(
        res: Result<T>?,
        acceptBody: (body: T) -> Unit,
        request: () -> Unit
    ) {
        viewState.setRequest(request)
        Log.d(Const.TAG_LOG, "received result")
        if(res == null) {
            Log.d(Const.TAG_LOG, "result == null")
        } else {
            if(res.response() == null) {
                Log.d(Const.TAG_LOG, "response == null and isError = ${res.isError}")
                Log.d(Const.TAG_LOG, "error = ${res.error()?.message}")
                res.error()?.printStackTrace()
                viewState.showConnectionError()
            }
        }
        res?.response()?.let {
            if (it.isSuccessful) {
                Log.d(Const.TAG_LOG, "successful response")
                it.body()?.let { students ->
                    acceptBody(students)
                }
            } else {
                Log.d(Const.TAG_LOG, "failed response")
                Log.d(Const.TAG_LOG, "mes = ${it.message()}")
                viewState.showConnectionError()

            }
        }
    }
}