package com.summer.itis.curatorapp.model.theme

import com.google.gson.annotations.SerializedName
import com.summer.itis.curatorapp.model.skill.Skill
import com.summer.itis.curatorapp.model.skill.Subject
import com.summer.itis.curatorapp.model.common.Identified
import java.util.*

abstract class AbsTheme: Identified {

    override lateinit var id: String
    lateinit var title: String
    lateinit var description: String
    lateinit var subject: Subject

    var skills: MutableList<Skill> = ArrayList()

    @SerializedName("skills_id")
    var skillsIds: MutableList<String> = ArrayList()

    fun setSkillsIds() {
        for(skill in skills) {
            skillsIds.add(skill.id)
        }
    }
}