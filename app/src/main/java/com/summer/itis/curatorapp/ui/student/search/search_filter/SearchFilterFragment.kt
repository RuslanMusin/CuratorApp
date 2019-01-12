package com.summer.itis.curatorapp.ui.student.search.search_filter

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.afollestad.materialdialogs.MaterialDialog
import com.arellomobile.mvp.presenter.InjectPresenter
import com.google.gson.reflect.TypeToken
import com.summer.itis.curatorapp.R
import com.summer.itis.curatorapp.model.skill.Skill
import com.summer.itis.curatorapp.model.user.Student
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragment
import com.summer.itis.curatorapp.ui.base.navigation_base.NavigationView
import com.summer.itis.curatorapp.ui.student.search.choose_skill.ChooseAddSkillFragment
import com.summer.itis.curatorapp.ui.student.search.choose_skill_main.ChooseSkillFragment
import com.summer.itis.curatorapp.ui.student.search.edit_choose_skill.EditChooseLIstFragment
import com.summer.itis.curatorapp.ui.student.student_list.StudentAdapter
import com.summer.itis.curatorapp.utils.Const
import com.summer.itis.curatorapp.utils.Const.ADD_SKILL
import com.summer.itis.curatorapp.utils.Const.CHOOSE_SKILL
import com.summer.itis.curatorapp.utils.Const.COURSE_KEY
import com.summer.itis.curatorapp.utils.Const.EDIT_CHOOSED_SKILLS
import com.summer.itis.curatorapp.utils.Const.FILTERS
import com.summer.itis.curatorapp.utils.Const.SKILL_KEY
import com.summer.itis.curatorapp.utils.Const.gsonConverter
import kotlinx.android.synthetic.main.fragment_search_filter.*
import kotlinx.android.synthetic.main.toolbar_back_done.*
import java.util.*


class SearchFilterFragment : BaseFragment<SearchFilterPresenter>(), SearchFilterView, View.OnClickListener {

    lateinit override var mainListener: NavigationView
    private lateinit var adapter: StudentAdapter

    lateinit var students: MutableList<Student>

    @InjectPresenter
    lateinit var presenter: SearchFilterPresenter

    private var listCourses: MutableList<String> = ArrayList()
    private var listSkills: MutableList<String> = ArrayList()
    private var skills: MutableList<Skill> = ArrayList()
    private var courses: MutableList<Long> = ArrayList()

    private var selectedYears: MutableList<Int> = ArrayList()

    private var imageViews: MutableList<ImageView> = ArrayList()
    private var liViews: MutableList<LinearLayout> = ArrayList()
    private lateinit var checkListener: View.OnClickListener

    companion object {

        const val TAG_SKILLS = "TAG_SKILLS"

        fun newInstance(args: Bundle, navigationView: NavigationView): Fragment {
            val fragment = SearchFilterFragment()
            fragment.arguments = args
            fragment.mainListener = navigationView
            return fragment
        }

        fun newInstance(navigationView: NavigationView): Fragment {
            val fragment = SearchFilterFragment()
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

        if(arguments != null) {
            arguments?.let {
                val skillType = object : TypeToken<ArrayList<Skill>>() {}.type
                val skillsJson = it.getString(SKILL_KEY)
                skills = gsonConverter.fromJson(skillsJson, skillType)

                val coursesType = object : TypeToken<ArrayList<Long>>() {}.type
                val courseJson = it.getString(COURSE_KEY)
                courses = gsonConverter.fromJson(courseJson, coursesType)
            }
        } else {
            loadCourses()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_search_filter, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        setToolbarData()
        setListeners()
        setData()
    }

    private fun setData() {
        if(skills.size == 0) {
            tv_added_skills.text = getString(R.string.doesnt_matter_for_all)
        } else {
            tv_added_skills.visibility = View.GONE
            for (skill in skills) {
                addSkillView(skill)
            }
        }
        if(courses.size == 0) {
            loadCourses()
        }
        if(courses.size != 4) {
            tv_added_courses.text = getCoursesText()
        } else {
            tv_added_courses.text = getString(R.string.doesnt_matter)
        }

    }

    private fun setToolbarData() {
        mainListener.setToolbar(toolbar_back_done)
        toolbar_title.text = getString(R.string.filter)
    }

    private fun setListeners() {
        btn_back.setOnClickListener(this)
        btn_ok.setOnClickListener(this)
        tv_choose_course.setOnClickListener(this)
        tv_add_skill.setOnClickListener(this)
        checkListener = object: View.OnClickListener{
            override fun onClick(v: View?) {
                val ivRemove = v as ImageView
                val index = imageViews.indexOf(ivRemove)
                val liSkill = liViews[index]
                li_added_skills.removeView(liSkill)
                liViews.removeAt(index)
                imageViews.removeAt(index)
                skills.removeAt(index)

                if(liViews.size == 0) {
                    tv_added_skills.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onClick(v: View) {

        when(v.id) {

            R.id.btn_back -> backFragment()

            R.id.tv_add_skill -> addSkill()

            R.id.tv_choose_course -> chooseCourse()

            R.id.btn_ok -> saveFilters()
        }
    }

    private fun saveFilters() {
        val intent = Intent()

        val skillsJson = gsonConverter.toJson(skills)
        intent.putExtra(SKILL_KEY, skillsJson)

        val coursesJson = gsonConverter.toJson(courses)
        intent.putExtra(COURSE_KEY, coursesJson)
        targetFragment?.onActivityResult(FILTERS, Activity.RESULT_OK, intent)
        mainListener.hideFragment()
    }

    private fun addSkill() {
        val fragment = ChooseAddSkillFragment.newInstance(mainListener)
        fragment.setTargetFragment(this, CHOOSE_SKILL)
        mainListener.showFragment(this, fragment)
    }

    private fun chooseCourse() {
        selectedYears.clear()
        listCourses.clear()
        for(year in courses) {
            selectedYears.add((year - 1).toInt())
        }
        this.activity?.let {
            MaterialDialog.Builder(it)
                .title(R.string.choose_course)
                .items(R.array.courses)
                .itemsCallbackMultiChoice(selectedYears.toTypedArray(), { dialog, which, text ->
                    listCourses = (text.toList() as List<String>).toMutableList()
                    tv_added_courses.text = getListString(listCourses)
                    if(courses.size != 0) {
                        courses.clear()
                        selectedYears.clear()
                    }
                    selectedYears.addAll(which)
                    for(i in which.indices) {
                        courses.add((which[i] + 1).toLong())
                    }
                    if(courses.size == 0) {
                        loadCourses()
                    }
                    if(courses.size == 4) {
                        tv_added_courses.text = getString(R.string.doesnt_matter)
                    }
                    true
                })
                .positiveText(R.string.choose)
                .show()
        }
    }

    override fun onActivityResult(reqCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(reqCode, resultCode, data)

        if(resultCode == Activity.RESULT_OK) {

            when(reqCode) {

                CHOOSE_SKILL -> {
                    data?.let {
                        val skillJson = it.getStringExtra(SKILL_KEY)
                        val skill = gsonConverter.fromJson(skillJson, Skill::class.java)
                        if(!skills.contains(skill)) {
                            skills.add(skill)
                            if (liViews.size == 0) {
                                tv_added_skills.visibility = View.GONE
                            }
                            addSkillView(skill)
                        }
                    }
                }
            }
        }
    }

    private fun addSkillView(skill: Skill) {
        val view: View = layoutInflater.inflate(R.layout.item_skill_clear_margin_off, li_added_skills,false)
        val ivRemoveSkill: ImageView = view.findViewById(R.id.iv_remove_skill)
        val tvAddedSkill: TextView = view.findViewById(R.id.tv_added_skill_name)

        ivRemoveSkill.setOnClickListener(checkListener)
        tvAddedSkill.text = skill.name
        imageViews.add(ivRemoveSkill)
        liViews.add(view as LinearLayout)

        li_added_skills.addView(view)
    }

    private fun getListString(list: List<String>): String {
        var listString = ""
        for((i,item) in list.withIndex()) {
            listString += item
            if(i != list.lastIndex) {
                listString += " , "
            }

        }
        listString.removeSuffix(",")
        return listString
    }

    private fun getSkillsText(): String {
        listSkills.clear()
        for(i in skills.indices) {
            listSkills.add(skills[i].name)
        }
        return getListString(listSkills)
    }

    private fun getCoursesText(): String {
        listCourses.clear()
        val courseArr = resources.getStringArray(R.array.courses)
        for(i in courses.indices) {
            listCourses.add(courseArr[(courses[i] - 1).toInt()])
        }
        return getListString(listCourses)
    }

    private fun loadCourses() {
        for(i in 1..4) {
            courses.add(i.toLong())
        }
    }
}