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

    @Expose
    override lateinit var id: String

    lateinit var email: String
    @Expose
    lateinit var name: String
    @SerializedName("last_name")
    lateinit var lastname: String
    @Expose
    lateinit var patronymic: String
    var photoUrl: String = STUB_PATH
    @Expose
    var description: String = "standart description"
    var isStandartPhoto: Boolean = true
    var status: String = OFFLINE_STATUS

    var skills: MutableList<Skill> = ArrayList()
    @Transient
    var suggestions: MutableList<SuggestionTheme> = ArrayList()
    @Transient
    var themes: MutableList<Theme> = ArrayList()
    @Transient
    var works: MutableList<Work> = ArrayList()

    fun getFullName(): String {
        return "$lastname $name $patronymic"
    }
}