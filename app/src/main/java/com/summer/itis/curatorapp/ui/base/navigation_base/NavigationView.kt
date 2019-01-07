package com.summer.itis.curatorapp.ui.base.navigation_base

import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar
import com.summer.itis.curatorapp.ui.base.base_first.activity.BaseActView

interface NavigationView: BaseActView {

    fun supportActionBar(toolbar: Toolbar)

    fun pushFragments(fragment: Fragment, shouldAdd: Boolean)

    fun showFragment(lastFragment: Fragment, fragment: Fragment)

    fun hideFragment()

    fun onBackPressed()

    fun hideBottomNavigation()

    fun showBottomNavigation()

    fun changeWindowsSoftInputMode(mode: Int)
}