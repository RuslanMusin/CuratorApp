package com.summer.itis.curatorapp.ui.theme.theme_list

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.*
import com.arellomobile.mvp.presenter.InjectPresenter
import com.summer.itis.curatorapp.R
import com.summer.itis.curatorapp.ui.base.base_custom.SearchListener
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragment
import com.summer.itis.curatorapp.ui.base.navigation_base.NavigationView
import com.summer.itis.curatorapp.ui.theme.theme_list.tab_fragment.ReloadableListView
import com.summer.itis.curatorapp.ui.theme.theme_list.tab_fragment.my_theme_list.MyThemeListFragment
import com.summer.itis.curatorapp.ui.theme.theme_list.tab_fragment.suggestion_list.SuggestionListFragment
import com.summer.itis.curatorapp.utils.AppHelper
import com.summer.itis.curatorapp.utils.Const.ID_KEY
import com.summer.itis.curatorapp.utils.Const.SUGGESTIONS_LIST
import com.summer.itis.curatorapp.utils.Const.TAG_LOG
import com.summer.itis.curatorapp.widget.FragViewPagerAdapter
import kotlinx.android.synthetic.main.fragment_theme_tabs.*

class ThemeListFragment : BaseFragment<ThemeListPresenter>(), ThemeListView {

    @InjectPresenter
    lateinit var presenter: ThemeListPresenter

    override lateinit var mainListener: NavigationView

    private var currentType: String? = null

    private var fragments: MutableList<Fragment> = ArrayList()
    lateinit private var currentFragment: SearchListener

    companion object {

        const val TAG_SKILLS = "TAG_SKILLS"

        const val EDIT_SKILLS = 1

        fun newInstance(args: Bundle, navigationView: NavigationView): Fragment {
            val fragment = ThemeListFragment()
            fragment.arguments = args
            fragment.mainListener = navigationView
            return fragment
        }

        fun newInstance(navigationView: NavigationView): Fragment {
            val fragment = ThemeListFragment()
            fragment.mainListener = navigationView
            return fragment
        }
    }

    override fun showBottomNavigation() {
        mainListener.showBottomNavigation()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_theme_tabs, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        mainListener.setToolbar(toolbar)
        mainListener.setToolbarTitle(getString(R.string.themes))
        setupViewPager(viewpager)
        tab_layout.setupWithViewPager(viewpager)
        viewpager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tab_layout))
        setTabListener()
        mainListener.hideLoading()
    }

    private fun setTabListener() {
        tab_layout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                Log.d(TAG_LOG, "on tab selected")
                viewpager.currentItem = tab.position
                (fragments[tab.position] as ReloadableListView).reloadList()
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })
    }


    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = FragViewPagerAdapter(childFragmentManager)
        val args = Bundle()
        args.putString(ID_KEY, AppHelper.currentCurator.id)
        fragments.add(SuggestionListFragment.newInstance(args, mainListener))
        fragments.add(MyThemeListFragment.newInstance(args, mainListener))
        adapter.addFragment(fragments[0], getString(R.string.suggestions))
        adapter.addFragment(fragments[1], getString(R.string.my_themes))
        this.currentType = SUGGESTIONS_LIST
        viewPager.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.search_menu, menu)
        menu?.let { setSearchMenuItem(it) }
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun setSearchMenuItem(menu: Menu) {
        val searchItem = menu.findItem(R.id.action_search)

        val searchView: SearchView = searchItem.actionView as SearchView
        val finalSearchView = searchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
//                presenter.loadOfficialTestsByQUery(query)

                if (!finalSearchView.isIconified) {
                    finalSearchView.isIconified = true
                }
                searchItem.collapseActionView()
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                val pos = viewpager.currentItem
                currentFragment = fragments[pos] as SearchListener
                currentFragment.findByQuery(newText)
                return false
            }
        })

    }

}