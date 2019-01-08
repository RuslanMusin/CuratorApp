package com.summer.itis.curatorapp.ui.theme.edit_theme

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
import com.arellomobile.mvp.presenter.InjectPresenter
import com.summer.itis.curatorapp.R
import com.summer.itis.curatorapp.model.skill.Skill
import com.summer.itis.curatorapp.model.skill.Subject
import com.summer.itis.curatorapp.model.theme.Suggestion
import com.summer.itis.curatorapp.model.theme.Theme
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragment
import com.summer.itis.curatorapp.ui.base.navigation_base.NavigationView
import com.summer.itis.curatorapp.ui.student.search.choose_skill.ChooseAddSkillFragment
import com.summer.itis.curatorapp.utils.Const
import com.summer.itis.curatorapp.utils.Const.ADD_THEME_TYPE
import com.summer.itis.curatorapp.utils.Const.ALL_CHOOSED
import com.summer.itis.curatorapp.utils.Const.CHOOSE_SKILL
import com.summer.itis.curatorapp.utils.Const.EDIT_SUGGESTION
import com.summer.itis.curatorapp.utils.Const.SKILL_KEY
import com.summer.itis.curatorapp.utils.Const.THEME_KEY
import com.summer.itis.curatorapp.utils.Const.gsonConverter
import kotlinx.android.synthetic.main.fragment_edit_theme.*
import kotlinx.android.synthetic.main.toolbar_back_done.*
import java.util.*

class EditThemeFragment : BaseFragment<EditThemePresenter>(), EditThemeView, View.OnClickListener {

    private lateinit var theme: Theme
    private var suggestion: Suggestion? = null

    private var type = ADD_THEME_TYPE

    override lateinit var mainListener: NavigationView

    private var subject: Subject? = null

    private var studentType = ALL_CHOOSED

    @InjectPresenter
    lateinit var presenter: EditThemePresenter

    private var skills: MutableList<Skill> = ArrayList()
    private var listSkills: MutableList<String> = ArrayList()

    private var imageViews: MutableList<ImageView> = ArrayList()
    private var liViews: MutableList<LinearLayout> = ArrayList()

    private lateinit var checkListener: View.OnClickListener


    companion object {

        fun newInstance(args: Bundle, mainListener: NavigationView): Fragment {
            val fragment = EditThemeFragment()
            fragment.arguments = args
            fragment.mainListener = mainListener
            return fragment
        }

        fun newInstance(mainListener: NavigationView): Fragment {
            val fragment = EditThemeFragment()
            fragment.mainListener = mainListener
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            theme = gsonConverter.fromJson(it.getString(THEME_KEY), Theme::class.java)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_edit_theme, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initViews() {
        setToolbarData()
        setListeners()
        setThemeData()
    }

    private fun setThemeData() {
        et_theme_name.setText(theme.title)
        et_theme_desc.setText(theme.description)

        skills = theme.skills
        subject = theme.subject

        if(skills.size == 0) {
            tv_added_skills.text = getString(R.string.doesnt_matter_for_all)
        } else {
            tv_added_skills.visibility = View.GONE
            for (skill in skills) {
                addSkillView(skill)
            }
        }
    }

    private fun setToolbarData() {
        mainListener.setToolbar(toolbar_back_done)
        toolbar_title.text = getString(R.string.edit_theme)
    }

    private fun setListeners() {
        btn_ok.setOnClickListener(this)
        btn_back.setOnClickListener(this)
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
//                textViews.removeAt(index)

                if(liViews.size == 0) {
                    tv_added_skills.visibility = View.VISIBLE
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
//        tvAddedLevel.content = getString(R.string.skill_level, skill.level)
        imageViews.add(ivRemoveSkill)
        liViews.add(view as LinearLayout)

        li_added_skills.addView(view)
    }

    override fun onClick(v: View) {
        when (v.id) {

            R.id.btn_ok -> {
                if(validateData()) {
                    theme.title = et_theme_name.text.toString()
                    theme.description = et_theme_desc.text.toString()
                    theme.skills = skills
                    presenter.updateTheme(theme)
                } else {
                    mainListener.showSnackBar(getString(R.string.invalid_fields))
                }
            }

            R.id.btn_back -> backFragment()

            R.id.tv_add_skill -> addSkill()
        }
    }

    private fun addSkill() {
        /*val fragment = ChooseSkillFragment.newInstance(mainListener)
        fragment.setTargetFragment(this, ADD_SKILL)
        mainListener.showFragment(SHOW_THEMES, this, fragment)*/
        val fragment = ChooseAddSkillFragment.newInstance(mainListener)
        fragment.setTargetFragment(this, Const.CHOOSE_SKILL)
        mainListener.showFragment(this, fragment)
    }

    override fun returnEditResult(intent: Intent?) {
        targetFragment?.onActivityResult(EDIT_SUGGESTION, Activity.RESULT_OK, intent)
        mainListener.hideFragment()
    }
    override fun onActivityResult(reqCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(reqCode, resultCode, data)

        if(resultCode == Activity.RESULT_OK) {

            when(reqCode) {

                CHOOSE_SKILL -> {
                    data?.let {
                        val skillJson = it.getStringExtra(SKILL_KEY)
                        val skill = gsonConverter.fromJson(skillJson, Skill::class.java)
                        skills.add(skill)
                        /*val skillText = "${skill.name} ${getString(R.string.level)} ${skill.level}"
                        listSkills.add(skillText)
                        tv_added_skills.content = getListString(listSkills)*/
                        tv_added_skills.visibility = View.GONE
                        addSkillView(skill)
                    }
                }
            }
        }
    }

    private fun validateData(): Boolean{
        val name = et_theme_name.text.toString()
        val desc  = et_theme_desc.text.toString()
        if(name.equals("") || desc.equals("")) {
            return false
        } else {
            return true
        }
    }

    private fun getListString(list: List<String>): String {
        var listString: String = ""
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
        if(skills.size != 0) {
            listSkills.clear()
            for (i in skills.indices) {
                listSkills.add(skills[i].name)
            }
            return getListString(listSkills)
        } else {
            return getString(R.string.doesnt_matter_for_all)
        }
    }
}
