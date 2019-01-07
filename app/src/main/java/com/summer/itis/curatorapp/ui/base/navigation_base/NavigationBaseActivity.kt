package com.summer.itis.curatorapp.ui.base.navigation_base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.summer.itis.curatorapp.R
import com.summer.itis.curatorapp.ui.base.base_first.activity.BaseActivity
import com.summer.itis.curatorapp.ui.curator.curator_item.view.CuratorFragment
import com.summer.itis.curatorapp.ui.student.student_list.StudentListFragment
import com.summer.itis.curatorapp.ui.theme.theme_list.ThemeListFragment
import com.summer.itis.curatorapp.ui.work.works.WorkListFragment
import com.summer.itis.curatorapp.utils.AppHelper
import com.summer.itis.curatorapp.utils.Const.ID_KEY
import com.summer.itis.curatorapp.utils.Const.TAG_LOG
import kotlinx.android.synthetic.main.activity_base.*
import java.util.*
import kotlin.collections.HashMap


//АКТИВИТИ РОДИТЕЛЬ ДЛЯ ОСНОВНОЙ НАВИГАЦИИ(БОКОВОЙ). ЮЗАТЬ МЕТОДЫ supportActionBar И setBackArrow(ЕСЛИ НУЖНА СТРЕЛКА НАЗАД)
class NavigationBaseActivity : BaseActivity<NavigationPresenter>(), NavigationView {

    @InjectPresenter
    override lateinit var presenter: NavigationPresenter

    private lateinit var stacks: HashMap<String, Stack<Fragment>>
    private lateinit var relativeTabs: HashMap<String, String>

    var currentTab: String = TAB_WORKS
    private var showTab: String = SHOW_WORKS

    companion object {

        const val TAG_NAVIG_ACT = "TAG_NAVIG_ACTIVITY"

        const val TAB_PROFILE = "tab_profile"
        const val TAB_STUDENTS = "tab_students"
        const val TAB_THEMES = "tab_themes"
        const val TAB_WORKS = "tab_works"
        const val SHOW_PROFILE = "show_profile"
        const val SHOW_THEMES = "show_theme"
        const val SHOW_WORKS = "show_works"

        fun start(context: Context) {
            val intent = Intent(context, NavigationBaseActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        initBottomNavigation()

        stacks = HashMap()
        stacks[TAB_PROFILE] = Stack()
        stacks[TAB_THEMES] = Stack()
        stacks[TAB_WORKS] = Stack()
        stacks[SHOW_PROFILE] = Stack()
        stacks[SHOW_THEMES] = Stack()
        stacks[SHOW_WORKS] = Stack()

        relativeTabs = HashMap()
        relativeTabs[TAB_PROFILE] = SHOW_PROFILE
        relativeTabs[TAB_THEMES] = SHOW_THEMES
        relativeTabs[TAB_WORKS] = SHOW_WORKS

        bottom_navigation.selectedItemId = R.id.action_works
    }

    override fun supportActionBar(toolbar: Toolbar) {
        if(supportActionBar == null) {
            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowHomeEnabled(true)
        }
    }

    private fun initBottomNavigation() {
        val bottomNavigationView = findViewById<View>(R.id.bottom_navigation) as BottomNavigationView
        bottomNavigationView.isItemHorizontalTranslationEnabled = false
        bottomNavigationView.setOnNavigationItemSelectedListener(
                object : BottomNavigationView.OnNavigationItemSelectedListener {
                    override fun onNavigationItemSelected(item: MenuItem): Boolean {
                        when (item.getItemId()) {
                            R.id.action_profile -> {
                                selectedTab(TAB_PROFILE)
                            }

                            R.id.action_works -> {
                                selectedTab(TAB_WORKS)
                            }

                            R.id.action_themes -> {
                                selectedTab(TAB_THEMES)
                            }
                        }
                        return true
                    }
                })
    }

    private fun selectedTab(tabId: String) {
        currentTab = tabId
        relativeTabs[currentTab]?.let { showTab = it }
        val size = stacks[tabId]?.size
        setToolbar(null)
        if (size == 0) {
            when(tabId) {

                TAB_PROFILE -> showProfile(tabId)

                TAB_STUDENTS -> showStudents(tabId)

                TAB_THEMES -> showThemes(tabId)

                TAB_WORKS -> showWorks(tabId)
            }
        } else {
            if(stacks[showTab]?.size == 0) {
                stacks[currentTab]?.lastElement()?.let {
                    pushFragments(it, false)
                }
            } else {
                stacks[showTab]?.lastElement()?.let {
                    val ft = supportFragmentManager.beginTransaction()
                    ft.replace(R.id.container, it)
                         ft.commit()
                }
            }
        }
    }

    override fun pushFragments(fragment: Fragment, shouldAdd: Boolean) {
        showBottomNavigation()
        if (shouldAdd) {
            stacks[currentTab]?.push(fragment)
        }
        val manager = supportFragmentManager
        val ft = manager.beginTransaction()
        ft.replace(R.id.container, fragment)
        ft.commit()
    }

    override fun onBackPressed() {
        showBottomNavigation()
        if(stacks[showTab]?.size!! > 1) {
            hideFragment()
        } else {
            popCurrentFragment()
        }
    }

    private fun popCurrentFragment() {
        val size = stacks.get(currentTab)?.size
        if(size == 1){
            finish();
            return;
        }
        popFragments();
    }

    fun popFragments() {
        val fragment = stacks[currentTab]?.size?.minus(2)?.let { stacks[currentTab]?.elementAt(it) }

        stacks[currentTab]?.pop()

        val manager = supportFragmentManager
        val ft = manager.beginTransaction()
        fragment?.let { ft.replace(R.id.container, it) }
        ft.commit()
    }

    override fun hideFragment() {
        val num = stacks[showTab]?.size?.minus(2)
        Log.d(TAG_LOG, "num = ${num} and size = ${stacks[showTab]?.size}")
        val fragment = num?.let { stacks[showTab]?.elementAt(it) }

        val fragmentBefore = stacks[showTab]?.pop()
        val manager = supportFragmentManager
        val ft = manager.beginTransaction()
        fragmentBefore?.let { ft.hide(it).remove(fragmentBefore) }
        val size = stacks.get(showTab)?.size
        if(size!! == 1){
            stacks[showTab]?.pop()
        }
        fragment?.let { ft.show(it) }

        ft.commit()
    }

    override fun showFragment(lastFragment: Fragment, fragment: Fragment) {
        if(stacks[showTab]?.size == 0) {
            stacks[showTab]?.push(lastFragment)
        }
        stacks[showTab]?.push(fragment)
        val manager = supportFragmentManager
        val ft = manager.beginTransaction()
        lastFragment.let {ft.hide(it).add(R.id.container, fragment).show(fragment)}
        ft.commit()
    }

    private fun showProfile(tabId: String) {
        Log.d(TAG_NAVIG_ACT, "curator start")
        val args: Bundle = Bundle()
        args.putString(ID_KEY, AppHelper.currentCurator.id)
        val fragment = CuratorFragment.newInstance(args, this)
        pushFragments(fragment, true)
    }

    private fun showStudents(tabId: String) {
        val fragment = StudentListFragment.newInstance(this)
        pushFragments(fragment, true)
    }

    private fun showThemes(tabId: String) {
        val fragment = ThemeListFragment.newInstance(this)
        pushFragments(fragment, true)
    }

    private fun showWorks(tabId: String) {
        val args = Bundle()
        args.putString(ID_KEY, AppHelper.currentCurator.id)
        val fragment = WorkListFragment.newInstance(args,this)
        pushFragments(fragment, true)
    }

    override fun hideBottomNavigation() {
        bottom_navigation.visibility = View.GONE
    }

    override fun showBottomNavigation() {
        bottom_navigation.visibility = View.VISIBLE
        changeWindowsSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
    }

    override fun changeWindowsSoftInputMode(mode: Int) {
        this.window.setSoftInputMode(mode);
    }

    override fun setToolbar(toolbar: Toolbar?) {
        super.setToolbar(toolbar)
    }

}
