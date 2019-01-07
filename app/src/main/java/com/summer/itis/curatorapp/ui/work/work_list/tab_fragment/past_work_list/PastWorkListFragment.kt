package com.summer.itis.curatorapp.ui.work.work_list.tab_fragment.past_work_list

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.summer.itis.curatorapp.R
import com.summer.itis.curatorapp.model.user.Curator
import com.summer.itis.curatorapp.model.work.Work
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragment
import com.summer.itis.curatorapp.ui.base.navigation_base.NavigationView
import com.summer.itis.curatorapp.ui.work.one_work_list.WorkAdapter
import com.summer.itis.curatorapp.ui.work.work_item.WorkFragment
import com.summer.itis.curatorapp.utils.AppHelper
import com.summer.itis.curatorapp.utils.Const
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.layout_recycler_list.*
import java.util.*
import java.util.regex.Pattern

class PastWorkListFragment : BaseFragment<PastWorkListPresenter>(), PastWorkListView, View.OnClickListener {

    lateinit var user: Curator
    lateinit override var mainListener: NavigationView
    private lateinit var adapter: WorkAdapter

    lateinit var works: MutableList<Work>

    @InjectPresenter
    lateinit var presenter: PastWorkListPresenter

    companion object {

        const val TAG_SKILLS = "TAG_SKILLS"

        const val EDIT_SKILLS = 1

        fun newInstance(args: Bundle, navigationView: NavigationView): Fragment {
            val fragment = PastWorkListFragment()
            fragment.arguments = args
            fragment.mainListener = navigationView
            return fragment
        }

        fun newInstance(navigationView: NavigationView): Fragment {
            val fragment = PastWorkListFragment()
            fragment.mainListener = navigationView
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_simple_recycler, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        user = AppHelper.currentCurator
        initViews()
    }

    private fun initViews() {
        initRecycler()
        setListeners()
    }

    private fun setListeners() {

    }

    override fun setNotLoading() {

    }

    override fun showLoading(disposable: Disposable) {
        pb_list.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        pb_list.visibility = View.GONE
    }

    override fun loadNextElements(i: Int) {
    }


    override fun changeDataSet(tests: List<Work>) {
        adapter.changeDataSet(tests)
        hideLoading()
    }

    override fun handleError(throwable: Throwable) {

    }

    private fun initRecycler() {
        adapter = WorkAdapter(ArrayList())
        val manager = LinearLayoutManager(activity as Activity)
        rv_list.layoutManager = manager
        rv_list.setEmptyView(tv_empty)
        adapter.attachToRecyclerView(rv_list)
        adapter.setOnItemClickListener(this)
    }

    override fun onItemClick(item: Work) {
        val args = Bundle()
        args.putString(Const.WORK_KEY, Const.gsonConverter.toJson(item))
        val fragment = WorkFragment.newInstance(args, mainListener)
        mainListener.pushFragments(fragment, true)
    }

    override fun findByQuery(query: String) {
        val pattern: Pattern = Pattern.compile("${query.toLowerCase()}.*")
        val list: MutableList<Work> = java.util.ArrayList()
        for(skill in works) {
            if(pattern.matcher(skill.theme.title.toLowerCase()).matches()) {
                list.add(skill)
            }
        }
        changeDataSet(list)
    }

    override fun onClick(view: View) {
        when(view.id) {

        }
    }
}
