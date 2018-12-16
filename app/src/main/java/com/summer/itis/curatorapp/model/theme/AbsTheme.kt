package com.summer.itis.curatorapp.model.theme

import com.summer.itis.curatorapp.model.skill.Skill
import com.summer.itis.curatorapp.model.skill.Subject
import com.summer.itis.curatorapp.model.common.Identified
import java.util.*

abstract class AbsTheme: Identified {

    override lateinit var id: String
    lateinit var title: String
    lateinit var description: String
    var subject: Subject = Subject(Integer.toString(Random().nextInt(100) + 1), "iOS")

    var skills: MutableList<Skill> = ArrayList()
}