package com.summer.itis.curatorapp.ui.work.work_item

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.summer.itis.curatorapp.R
import com.summer.itis.curatorapp.model.work.Work
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragment
import com.summer.itis.curatorapp.ui.base.navigation_base.NavigationView
import com.summer.itis.curatorapp.ui.description.DescriptionFragment
import com.summer.itis.curatorapp.ui.work.progress_card.show.StepListFragment
import com.summer.itis.curatorapp.utils.AppHelper
import com.summer.itis.curatorapp.utils.Const
import com.summer.itis.curatorapp.utils.Const.ID_KEY
import com.summer.itis.curatorapp.utils.Const.MAX_LENGTH
import com.summer.itis.curatorapp.utils.Const.OWNER_TYPE
import com.summer.itis.curatorapp.utils.Const.TYPE
import com.summer.itis.curatorapp.utils.Const.WATCHER_TYPE
import com.summer.itis.curatorapp.utils.Const.WORK_KEY
import kotlinx.android.synthetic.main.fragment_work.*
import kotlinx.android.synthetic.main.layout_expandable_text_view.*
import kotlinx.android.synthetic.main.toolbar_back.*

class WorkFragment: BaseFragment<WorkPresenter>(), WorkView, View.OnClickListener {

    override lateinit var mainListener: NavigationView
    lateinit var work: Work

    @InjectPresenter
    lateinit var presenter: WorkPresenter

    companion object {

        fun newInstance(args: Bundle, navigationView: NavigationView): Fragment {
            val fragment = WorkFragment()
            fragment.arguments = args
            fragment.mainListener = navigationView
            return fragment
        }

        fun newInstance(navigationView: NavigationView): Fragment {
            val fragment = WorkFragment()
            fragment.mainListener = navigationView
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_work, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            val workId = it.getString(ID_KEY)
            presenter.loadWork(workId)
        }
    }

    fun initViews() {
        setToolbarData()
        setListeners()
    }

    private fun setToolbarData() {
        mainListener.setToolbar(toolbar_back)
        toolbar_title.text = getString(R.string.work)
    }

    private fun setListeners() {
        btn_back.setOnClickListener(this)
        li_skills.setOnClickListener(this)
        li_desc.setOnClickListener(this)
    }

    override fun showWork(work: Work) {
        this.work = work
        tv_title.text = work.theme.title
        tv_curator.text = work.theme.curator?.getFullName()
        val name = work.theme.student?.getFullName()
        if(name == null) {
            tv_student.text = getString(R.string.theme_not_choosed)
        } else {
            tv_student.text = name
        }
        tv_subject.text = work.theme.subject.name
        tv_desc.text = AppHelper.cutLongDescription(work.theme.description, MAX_LENGTH)
    }

    override fun onClick(v: View) {
        when (v.id) {

            R.id.btn_back -> backFragment()

            R.id.li_skills -> showSteps()

            R.id.li_desc -> showDesc()
        }
    }

    private fun showDesc() {
        val args = Bundle()
        args.putString(Const.DESC_KEY, work.theme.description)
        val fragment = DescriptionFragment.newInstance(args, mainListener)
        mainListener.pushFragments(fragment, true)
    }

    private fun showSteps() {
        val args = Bundle()
        args.putString(WORK_KEY, work.id)
        if(AppHelper.currentCurator.id.equals(work.theme.curator?.id)) {
            args.putString(TYPE, OWNER_TYPE)
        } else {
            args.putString(TYPE, WATCHER_TYPE)
        }
        val fragment = StepListFragment.newInstance(args, mainListener)
        mainListener.pushFragments(fragment, true)
    }
}
