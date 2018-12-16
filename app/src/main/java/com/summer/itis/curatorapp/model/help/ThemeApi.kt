package com.summer.itis.curatorapp.model.help

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.summer.itis.curatorapp.model.theme.Theme
import java.util.*

class ThemeApi {

    @Expose(serialize = false)
    lateinit var id: String

    var title: String? = null
    var description: String? = null
    @SerializedName("curator")
    var curatorId: String? = null
    @SerializedName("student")
    var studentId: String? = null
    @SerializedName("subject")
    var subjectId: String? = null

    @SerializedName("date_creation")
    var dateCreation: Date? = null
    @SerializedName("date_acceptance")
    var dateAcceptance: Date? = null
    @SerializedName("skills")
    var skillsIds: MutableList<String> = ArrayList()

    constructor()

    constructor(theme: Theme) {
        this.id = theme.id
        this.title = theme.title
        this.description = theme.description
        this.curatorId = theme.curator?.id
        this.studentId = theme.student?.id
        this.subjectId = theme.subject?.id
        this.dateCreation = theme.dateCreation
        this.dateAcceptance = theme.dateAcceptance
        for(skill in theme.skills) {
            this.skillsIds.add(skill.id)
        }
    }
}