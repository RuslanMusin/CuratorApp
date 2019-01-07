package com.summer.itis.curatorapp.model.user

import com.google.gson.annotations.SerializedName
import com.summer.itis.curatorapp.model.common.Identified
import com.summer.itis.curatorapp.model.skill.Skill
import com.summer.itis.curatorapp.model.theme.Suggestion
import com.summer.itis.curatorapp.model.theme.Theme
import com.summer.itis.curatorapp.model.work.Work

abstract class User: Identified {

    override lateinit var id: String
    @SerializedName("username")
    lateinit var email: String
    lateinit var password: String
    lateinit var name: String
    @SerializedName("last_name")
    lateinit var lastname: String
    lateinit var patronymic: String
    var description: String = "standart description"
    @Transient
    var skills: MutableList<Skill> = ArrayList()

    @Transient
    var suggestions: MutableList<Suggestion> = ArrayList()
    @Transient
    var themes: MutableList<Theme> = ArrayList()
    @Transient
    var works: MutableList<Work> = ArrayList()

    @SerializedName("skills_id")
    var skillsIds: MutableList<String> = ArrayList()

    constructor() {}

    constructor(email: String, password: String) {
        this.email = email
        this.password = password
    }

    fun getFullName(): String {
        return "$lastname $name $patronymic"
    }

    fun setSkillsIds() {
        for(skill in skills) {
            skillsIds.add(skill.id)
        }
    }
}