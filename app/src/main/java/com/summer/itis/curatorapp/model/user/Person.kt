package com.summer.itis.curatorapp.model.user

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.summer.itis.curatorapp.model.skill.Skill
import com.summer.itis.curatorapp.model.theme.SuggestionTheme
import com.summer.itis.curatorapp.model.theme.Theme
import com.summer.itis.curatorapp.model.work.Work
import com.summer.itis.curatorapp.utils.Const.OFFLINE_STATUS
import com.summer.itis.curatorapp.utils.Const.STUB_PATH
import com.summer.itis.curatorapp.model.common.Identified

abstract class Person: Identified {

    override lateinit var id: String
    @Transient
    lateinit var email: String
    lateinit var name: String
    @SerializedName("last_name")
    lateinit var lastname: String
    lateinit var patronymic: String
    var description: String = "standart description"
    @Transient
    var skills: MutableList<Skill> = ArrayList()

    @Transient
    var suggestions: MutableList<SuggestionTheme> = ArrayList()
    @Transient
    var themes: MutableList<Theme> = ArrayList()
    @Transient
    var works: MutableList<Work> = ArrayList()

    @SerializedName("skills_id")
    var skillsIds: MutableList<String> = ArrayList()
        /*get() {
            val list: MutableList<String> = ArrayList()
            for(skill in skills) {
                list.add(skill.id)
            }
            skillsIds = list
            return list
        }*/

    fun getFullName(): String {
        return "$lastname $name $patronymic"
    }

    fun setSkillsIds() {
        for(skill in skills) {
            skillsIds.add(skill.id)
        }
    }
}