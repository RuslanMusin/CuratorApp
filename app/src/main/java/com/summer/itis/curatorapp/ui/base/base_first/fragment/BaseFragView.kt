package com.summer.itis.curatorapp.ui.base.base_first.fragment

import com.arellomobile.mvp.MvpView

interface BaseFragView: MvpView {

    fun showConnectionError()
    fun showConnectionError(errorMessage: Int)
    fun setRequest(request: () -> Unit)

    fun startTimeout(errorMessage: Int)
    fun startTimeout(request: () -> Unit)
    fun stopTimeout()

}