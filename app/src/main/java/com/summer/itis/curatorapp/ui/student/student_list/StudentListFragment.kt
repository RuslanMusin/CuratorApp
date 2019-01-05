package com.summer.itis.curatorapp.ui.student.student_list

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.*
import com.arellomobile.mvp.presenter.InjectPresenter
import com.google.gson.reflect.TypeToken
import com.summer.itis.curatorapp.R
import com.summer.itis.curatorapp.model.skill.Skill
import com.summer.itis.curatorapp.model.user.Student
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragment
import com.summer.itis.curatorapp.ui.base.navigation_base.NavigationBaseActivity.Companion.SHOW_THEMES
import com.summer.itis.curatorapp.ui.base.navigation_base.NavigationView
import com.summer.itis.curatorapp.ui.student.search.search_filter.SearchFilterFragment
import com.summer.itis.curatorapp.ui.student.student_item.StudentFragment
import com.summer.itis.curatorapp.utils.Const
import com.summer.itis.curatorapp.utils.Const.ADD_STUDENT
import com.summer.itis.curatorapp.utils.Const.COURSE_KEY
import com.summer.itis.curatorapp.utils.Const.FILTERS
import com.summer.itis.curatorapp.utils.Const.ID_KEY
import com.summer.itis.curatorapp.utils.Const.REQUEST_CODE
import com.summer.itis.curatorapp.utils.Const.SEND_THEME
import com.summer.itis.curatorapp.utils.Const.SKILL_KEY
import com.summer.itis.curatorapp.utils.Const.TAB_NAME
import com.summer.itis.curatorapp.utils.Const.TAG_LOG
import com.summer.itis.curatorapp.utils.Const.gsonConverter
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.layout_recycler_list.*
import kotlinx.android.synthetic.main.toolbar_add.*
import java.util.regex.Pattern
import kotlin.collections.ArrayList

class StudentListFragment : BaseFragment<StudentListPresenter>(), StudentListView, View.OnClickListener {

    lateinit override var mainListener: NavigationView
    private lateinit var adapter: StudentAdapter

    lateinit var students: MutableList<Student>

    @InjectPresenter
    lateinit var presenter: StudentListPresenter

    private var courses: MutableList<Long> = ArrayList()
    private var skills: MutableList<Skill> = ArrayList()

    private var lastQuery: String = ""

    private var requestCode = ADD_STUDENT


    companion object {

        const val TAG_SKILLS = "TAG_SKILLS"

        const val EDIT_SKILLS = 1

        fun newInstance(args: Bundle, navigationView: NavigationView): Fragment {
            val fragment = StudentListFragment()
            fragment.arguments = args
            fragment.mainListener = navigationView
            return fragment
        }

        fun newInstance(navigationView: NavigationView): Fragment {
            val fragment = StudentListFragment()
            fragment.mainListener = navigationView
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        arguments?.let {
            requestCode = it.getInt(REQUEST_CODE)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_student_list, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        presenter.loadStudents()
    }

    override fun showStudents(students: List<Student>) {
        this.students = students.toMutableList()
        changeDataSet(this.students)
    }

    private fun initViews() {
        setToolbarData()
        initRecycler()
        setListeners()
    }

    private fun setToolbarData() {
        mainListener.setToolbar(toolbar_add)
        btn_add.visibility = View.GONE
    }

    private fun setListeners() {
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


    override fun changeDataSet(tests: List<Student>) {
        adapter.changeDataSet(tests)
        hideLoading()
    }

    override fun handleError(throwable: Throwable) {

    }

    private fun initRecycler() {
        adapter = StudentAdapter(ArrayList())
        val manager = LinearLayoutManager(activity as Activity)
        rv_list.layoutManager = manager
        rv_list.setEmptyView(tv_empty)
        adapter.attachToRecyclerView(rv_list)
        adapter.setOnItemClickListener(this)
    }

    override fun onItemClick(item: Student) {
        val args = Bundle()
        args.putString(ID_KEY, item.id)
        args.putInt(REQUEST_CODE, requestCode)
        args.putString(TAB_NAME, SHOW_THEMES)
        val fragment = StudentFragment.newInstance(args, mainListener)
        fragment.setTargetFragment(this, requestCode)
        mainListener.showFragment(SHOW_THEMES, this, fragment)
//        mainListener.pushFragments(TAB_STUDENTS, fragment, true)
    }

    override fun onClick(v: View) {

        when(v.id) {

            R.id.btn_back -> backFragment()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.search_with_filter, menu)
        menu?.let { setSearchMenuItem(it) }
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun setSearchMenuItem(menu: Menu) {
        val searchItem = menu.findItem(R.id.action_search)

        val searchView: SearchView = searchItem.actionView as SearchView
        val finalSearchView = searchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                if (!finalSearchView.isIconified) {
                    finalSearchView.isIconified = true
                }
                searchItem.collapseActionView()
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                findFromList(newText)
                return false
            }
        })

        val filterItem = menu.findItem(R.id.action_filter)
        filterItem.setOnMenuItemClickListener {
            val args = Bundle()
            val skillsJson = gsonConverter.toJson(skills)
            args.putString(SKILL_KEY, skillsJson)
            val yearsJson = gsonConverter.toJson(courses)
            args.putString(COURSE_KEY, yearsJson)
            val fragment = SearchFilterFragment.newInstance(args, mainListener)
            fragment.setTargetFragment(this, FILTERS)
            mainListener.showFragment(SHOW_THEMES, this, fragment)
            false
        }

    }

    private fun findFromList(query: String) {
        lastQuery = query
        val pattern: Pattern = Pattern.compile(".*${query.toLowerCase()}.*")
        val list: MutableList<Student> = java.util.ArrayList()
        for(student in students) {
            if(pattern.matcher(student.getFullName().toLowerCase()).matches() && filter(student)) {
                Log.d(TAG_LOG, "matched student = ${student.getFullName()}")
                list.add(student)
            }
        }
        changeDataSet(list)
    }

    override fun onActivityResult(reqCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(reqCode, resultCode, data)

        if(resultCode == Activity.RESULT_OK) {

            when(reqCode) {

                ADD_STUDENT -> {
                    targetFragment?.onActivityResult(requestCode, Activity.RESULT_OK, data)
                    mainListener.hideFragment()
                }

                FILTERS -> {
                    data?.let {
                        val skillType = object : TypeToken<ArrayList<Skill>>() { }.type
                        val skillsJson = it.getStringExtra(Const.SKILL_KEY)
                        skills = Const.gsonConverter.fromJson(skillsJson, skillType)

                        val coursesType = object : TypeToken<ArrayList<Long>>() { }.type
                        val courseJson = it.getStringExtra(Const.COURSE_KEY)
                        courses = Const.gsonConverter.fromJson(courseJson, coursesType)

                        applyFilter()
                    }
                }

                SEND_THEME -> {
                    targetFragment?.onActivityResult(requestCode, Activity.RESULT_OK, data)
                    mainListener.hideFragment()
                }
            }
        }
    }

    private fun applyFilter() {
        findFromList(lastQuery)
    }

    private fun filter(student: Student): Boolean {
        if(courses.size != 0) {
            var yearFlag = false
            for (year in courses) {
                if (student.courseNumber == year) {
                    yearFlag = true
                }
            }
            if (yearFlag) {
                var skillFlag = true
                for (skillFilter in skills) {
                    var skillItemFlag = false
                    for (skill in student.skills) {
                        if (skill.equals(skillFilter)) {
                            skillItemFlag = true
                        }
                    }
                    if (skillItemFlag == false) {
                        skillFlag = false
                        break
                    }
                }
                if (skillFlag && yearFlag) {
                    return true
                }
            }
            return false
        } else {
            return true
        }
    }
}
