package com.summer.itis.curatorapp.ui.theme.theme_list.tab_fragment.my_theme_list

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.afollestad.materialdialogs.MaterialDialog
import com.arellomobile.mvp.presenter.InjectPresenter
import com.summer.itis.curatorapp.R
import com.summer.itis.curatorapp.model.theme.Theme
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragment
import com.summer.itis.curatorapp.ui.base.navigation_base.NavigationBaseActivity
import com.summer.itis.curatorapp.ui.base.navigation_base.NavigationView
import com.summer.itis.curatorapp.ui.theme.add_theme.AddThemeFragment
import com.summer.itis.curatorapp.ui.theme.theme_item.ThemeFragment
import com.summer.itis.curatorapp.utils.AppHelper
import com.summer.itis.curatorapp.utils.Const
import com.summer.itis.curatorapp.utils.Const.ALL_CHOOSED
import com.summer.itis.curatorapp.utils.Const.ID_KEY
import com.summer.itis.curatorapp.utils.Const.TAG_LOG
import com.summer.itis.curatorapp.utils.FormatterUtil
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_theme_list.*
import kotlinx.android.synthetic.main.layout_recycler_list.*
import java.util.regex.Pattern

class MyThemeListFragment : BaseFragment<MyThemeListPresenter>(), MyThemeListView, View.OnClickListener {

    lateinit override var mainListener: NavigationView
    private lateinit var adapter: ThemeAdapter

    lateinit var themes: MutableList<Theme>
    lateinit var userId: String

    @InjectPresenter
    lateinit var presenter: MyThemeListPresenter

    private var fakeStudentNum = 0

    companion object {

        const val TAG_SKILLS = "TAG_SKILLS"

        const val EDIT_SKILLS = 1

        fun newInstance(args: Bundle, navigationView: NavigationView): Fragment {
            val fragment = MyThemeListFragment()
            fragment.arguments = args
            fragment.mainListener = navigationView
            return fragment
        }

        fun newInstance(navigationView: NavigationView): Fragment {
            val fragment = MyThemeListFragment()
            fragment.mainListener = navigationView
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_theme_list, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            userId = it.getString(ID_KEY)
            initViews()
            presenter.loadSkills(userId)

        }
    }

    override fun showThemes(themes: List<Theme>) {
        this.themes = themes.toMutableList()
        Log.d(TAG_LOG, "date = ${themes[0].dateCreation}")
        Log.d(TAG_LOG, "dateStr = ${FormatterUtil.getStringFromDate(themes[0].dateCreation)}")
        changeDataSet(this.themes)
    }

  /*  private fun loadWorks() {
//        presenter.loadWorks(AppHelper.currentCurator.id)
        if(student.themes.size == 0) {
            themes = ArrayList()
            val skills = this.activity?.let { AppHelper.getSkillsList(it) }
            for (i in 1..10) {
                val theme = Theme()
                theme.id = "$i"
                val curator = AppHelper.currentCurator
                theme.curator = curator
                theme.curatorId = curator.id
//                suggestionTheme.curatorName = step.name
                theme.student = null
                theme.studentId = i.toString()
                val lastNum = if(10 - i > 3)  i + 3 else i + 10 - i
                theme.skills = skills?.subList(i, lastNum) as MutableList<Skill>

                theme.description = "Simple App for students"
                if(i % 2 == 0) {
                    theme.title = "Web-платформа для создания интеллектуальных систем"
                    val subject = Subject()
                    subject.name = "Интеллектуальные системы"
                    subject.id = "$i"
                    theme.subject = subject
                    theme.subjectId = subject.id
                } else {
                    theme.title = "Приложение для обмена книгами"
                    val subject = Subject()
                    subject.name = "Android"
                    subject.id = "$i"
                    theme.subject = subject
                    theme.subjectId = subject.id
                }
                themes.add(theme)
            }
            student.themes = themes

        } else {
            themes = student.themes
        }
        changeDataSet(themes)

    }*/

    private fun initViews() {
        initRecycler()
        setListeners()
    }

    private fun setListeners() {
        btn_add_theme.setOnClickListener(this)
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


    override fun changeDataSet(tests: List<Theme>) {
        adapter.changeDataSet(tests)
        hideLoading()
    }

    override fun handleError(throwable: Throwable) {

    }

    private fun initRecycler() {
        adapter = ThemeAdapter(ArrayList(), this)
        val manager = LinearLayoutManager(activity as Activity)
        rv_list.layoutManager = manager
        rv_list.setEmptyView(tv_empty)
        adapter.attachToRecyclerView(rv_list)
        adapter.setOnItemClickListener(this)

        rv_list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0 || dy < 0 && btn_add_theme.isShown())
                    btn_add_theme.hide()
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    btn_add_theme.show()
                }
                super.onScrollStateChanged(recyclerView, newState)
            }
        })
    }

    override fun onItemClick(item: Theme) {
        val args = Bundle()
        args.putString(Const.THEME_KEY, Const.gsonConverter.toJson(item))
        val fragment = ThemeFragment.newInstance(args, mainListener)
        mainListener.pushFragments(NavigationBaseActivity.TAB_THEMES, fragment, true)
    }

    override fun openStudentAction(adapterPosition: Int) {
        val theme = themes[adapterPosition]
        if(theme.targetType.equals(ALL_CHOOSED)) {
            this.activity?.let {
                MaterialDialog.Builder(it)
                        .title(R.string._should_user_fake_choose_theme)
                        .items(R.array.fake_students)
                        .itemsCallbackSingleChoice(0, MaterialDialog.ListCallbackSingleChoice { dialog, view, which, text ->
                            fakeStudentNum = which
                            true
                        })
                        .alwaysCallSingleChoiceCallback()
                        .positiveText(R.string.button_ok)
                        .negativeText(R.string.cancel)
                        .onNegative { dialog, which ->
                            dialog.dismiss()
                        }
                        .onPositive { dialog, which ->
                            this.context?.let { it1 ->
                                presenter.addFakeSuggestion(themes[adapterPosition], AppHelper.getFakeStudent(fakeStudentNum, it1))
                            }
                        }
                        .show()
            }
        } else {
            this.activity?.let {
                MaterialDialog.Builder(it)
                        .title(R.string.student_fake_theme)
                        .content(R.string.should_user_send_fake_theme)
                        .positiveText(R.string.button_ok)
                        .negativeText(R.string.cancel)
                        .onNegative { dialog, which ->
                            dialog.dismiss()
                        }
                        .onPositive { dialog, which ->
                            this.context?.let { it1 ->
                                presenter.addFakeStudentSuggestion(themes[adapterPosition], AppHelper.getFakeStudent(fakeStudentNum, it1))
                            }
                        }
                        .show()
            }
        }
    }

    override fun findByQuery(query: String) {
        val pattern: Pattern = Pattern.compile("${query.toLowerCase()}.*")
        val list: MutableList<Theme> = java.util.ArrayList()
        for(skill in themes) {
            if(pattern.matcher(skill.title.toLowerCase()).matches()) {
                list.add(skill)
            }
        }
        changeDataSet(list)
    }

    override fun onClick(view: View) {
        when(view.id) {

            R.id.btn_add_theme -> {

                val args = Bundle()
                args.putString(Const.ID_KEY, userId)
                val fragment = AddThemeFragment.newInstance(args, mainListener)
              /*  val tabLayout = parentFragment?.view?.findViewById<TabLayout>(R.id.tab_layout)
                tabLayout?.visibility = View.GONE*/
                mainListener.pushFragments(NavigationBaseActivity.TAB_THEMES, fragment, true)            }
        }
    }
}
