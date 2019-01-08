package com.summer.itis.curatorapp.ui.work.progress_card.show

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.summer.itis.curatorapp.R
import com.summer.itis.curatorapp.model.step.Step
import com.summer.itis.curatorapp.model.theme.Status
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragment
import com.summer.itis.curatorapp.ui.base.navigation_base.NavigationView
import com.summer.itis.curatorapp.ui.work.work_step.add_step.AddStepFragment
import com.summer.itis.curatorapp.ui.work.work_step.step.StepFragment
import com.summer.itis.curatorapp.utils.Const
import com.summer.itis.curatorapp.utils.Const.OWNER_TYPE
import com.summer.itis.curatorapp.utils.Const.STEP_KEY
import com.summer.itis.curatorapp.utils.Const.TAG_LOG
import com.summer.itis.curatorapp.utils.Const.TYPE
import com.summer.itis.curatorapp.utils.Const.WATCHER_TYPE
import com.summer.itis.curatorapp.utils.Const.WORK_KEY
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.layout_recycler_list.*
import kotlinx.android.synthetic.main.toolbar_add.*
import java.util.*

class StepListFragment : BaseFragment<StepListPresenter>(),
    StepListView, View.OnClickListener {

    lateinit var workId: String
    var type: String = OWNER_TYPE
    lateinit override var mainListener: NavigationView
    private lateinit var adapter: StepAdapter

    lateinit var steps: MutableList<Step>

    @InjectPresenter
    lateinit var presenter: StepListPresenter

    companion object {

        fun newInstance(args: Bundle, navigationView: NavigationView): Fragment {
            val fragment = StepListFragment()
            fragment.arguments = args
            fragment.mainListener = navigationView
            return fragment
        }

        fun newInstance(navigationView: NavigationView): Fragment {
            val fragment = StepListFragment()
            fragment.mainListener = navigationView
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        arguments?.let {
            workId = it.getString(WORK_KEY)
            type = it.getString(TYPE)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_steps, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        presenter.loadSteps(workId)
    }

    override fun showSteps(steps: List<Step>) {
        this.steps = steps.toMutableList()
        changeDataSet(this.steps)
    }

    private fun initViews() {
        setToolbarData()
        initRecycler()
        setListeners()
    }

    private fun setToolbarData() {
        mainListener.setToolbar(toolbar_add)
        toolbar_title.text = getString(R.string.card_progress)

    }

    private fun setListeners() {
        if(type.equals(WATCHER_TYPE)) {
            btn_add.visibility = View.GONE
        } else {
            btn_add.setOnClickListener(this)
        }
        btn_back.setOnClickListener(this)
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


    override fun changeDataSet(tests: List<Step>) {
        adapter.changeDataSet(tests)
        hideLoading()
    }

    override fun handleError(throwable: Throwable) {

    }

    private fun initRecycler() {
        adapter = StepAdapter(ArrayList(), this)
        val manager = LinearLayoutManager(activity as Activity)
        rv_list.layoutManager = manager
        rv_list.setEmptyView(tv_empty)
        adapter.attachToRecyclerView(rv_list)
        adapter.setOnItemClickListener(this)
    }

    override fun onItemClick(item: Step) {
        if(type.equals(OWNER_TYPE)) {
            val args = Bundle()
            args.putString(WORK_KEY, workId)
            args.putString(STEP_KEY, item.id)
            val fragment = StepFragment.newInstance(args, mainListener)
            mainListener.pushFragments(fragment, true)
        }
    }

    override fun onClick(v: View) {
        when (v.id) {

            R.id.btn_add -> addStep()

            R.id.btn_back -> backFragment()

        }
    }

    private fun addStep() {
        val args = Bundle()
        args.putString(Const.ID_KEY, workId)
        val fragment = AddStepFragment.newInstance(args, mainListener)
        mainListener.pushFragments(fragment, true)
    }

    override fun changeStatus(pos: Int, status: Status) {
        val step = steps[pos]
        Log.d(TAG_LOG, "step status = ${step.status.id} and status = ${status.id}")
        presenter.changeStatus(workId, step)
    }

}
