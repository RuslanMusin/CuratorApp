package com.summer.itis.curatorapp.ui.base.base_first.fragment

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import com.arellomobile.mvp.MvpAppCompatFragment
import com.summer.itis.curatorapp.model.user.User
import com.summer.itis.curatorapp.ui.base.navigation_base.NavigationView
import com.summer.itis.curatorapp.utils.AppHelper
import com.summer.itis.curatorapp.utils.Const
import com.summer.itis.curatorapp.utils.Const.CURATOR_TYPE
import com.summer.itis.curatorapp.utils.Const.PERSON_TYPE
import com.summer.itis.curatorapp.utils.Const.TAG_LOG
import com.summer.itis.curatorapp.utils.Const.USER_KEY
import com.summer.itis.curatorapp.utils.Const.gsonConverter

//СТАНДАРТНЫЙ БАЗОВЫЙ АКТИВИТИ ДЛЯ ТЕХ,КОМУ НЕ НУЖНА НАВИГАЦИЯ, НО ЕСТЬ СТРЕЛКА НАЗАД И ПРОГРЕСС БАР.
abstract class BaseFragment<Presenter: BaseFragPresenter<*>> : MvpAppCompatFragment(), BaseFragView {

    private var timer: CountDownTimer? = null

    abstract var mainListener: NavigationView

    protected fun backFragment() {
        mainListener.onBackPressed()
    }

    override fun showConnectionError() {
        mainListener.showConnectionError()
    }

    override fun showConnectionError(errorMessage: Int) {
        mainListener.showSnackBar(errorMessage)
    }

    override fun setRequest(request: () -> Unit) {
        mainListener.setRequest(request)
    }

    abstract fun showBottomNavigation()

    override fun startTimeout(errorMessage: Int) {
        timer?.cancel()
        timer = object : CountDownTimer(30000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                Log.d(TAG_LOG,"Wait Change Time = ${millisUntilFinished / 1000}")
            }

            override fun onFinish() {
                Log.d(TAG_LOG,"stop change cards")
                mainListener.showSnackBar(errorMessage)
            }
        }.start()
    }

    override fun startTimeout(request: () -> Unit) {
        timer?.cancel()
        timer = object : CountDownTimer(30000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                Log.d(TAG_LOG,"Wait Change Time = ${millisUntilFinished / 1000}")
            }

            override fun onFinish() {
                Log.d(TAG_LOG,"stop change cards")
                mainListener.setRequest(request)
                mainListener.showConnectionError()
            }
        }.start()
    }

    override fun stopTimeout() {
        timer?.cancel()
    }

}