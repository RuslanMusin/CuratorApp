package com.summer.itis.curatorapp.ui.base.base_first.fragment

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import com.arellomobile.mvp.MvpAppCompatFragment
import com.summer.itis.curatorapp.model.user.User
import com.summer.itis.curatorapp.ui.base.navigation_base.NavigationView
import com.summer.itis.curatorapp.utils.AppHelper
import com.summer.itis.curatorapp.utils.Const
import com.summer.itis.curatorapp.utils.Const.CURATOR_TYPE
import com.summer.itis.curatorapp.utils.Const.PERSON_TYPE
import com.summer.itis.curatorapp.utils.Const.USER_KEY
import com.summer.itis.curatorapp.utils.Const.gsonConverter

//СТАНДАРТНЫЙ БАЗОВЫЙ АКТИВИТИ ДЛЯ ТЕХ,КОМУ НЕ НУЖНА НАВИГАЦИЯ, НО ЕСТЬ СТРЕЛКА НАЗАД И ПРОГРЕСС БАР.
abstract class BaseFragment<Presenter: BaseFragPresenter<*>> : MvpAppCompatFragment(), BaseFragView {

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

}