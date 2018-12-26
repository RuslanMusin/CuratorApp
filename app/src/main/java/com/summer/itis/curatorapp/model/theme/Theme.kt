package com.summer.itis.curatorapp.model.theme

import com.google.gson.annotations.SerializedName
import com.summer.itis.curatorapp.model.user.Curator
import com.summer.itis.curatorapp.model.user.Student
import com.summer.itis.curatorapp.utils.Const.ALL_CHOOSED
import java.util.*

class Theme: AbsTheme() {
    @SerializedName("curator_id")
    var curatorId: String? = null
    @SerializedName("student_id")
    var studentId: String? = null
    @SerializedName("subject_id")
    var subjectId: String? = null

    @SerializedName("date_creation")
    lateinit var dateCreation: Date
    @SerializedName("date_acceptance")
    var dateAcceptance: Date? = null

    var curator: Curator? = null
    var student: Student? = null

    var targetType: String = ALL_CHOOSED

    fun setApiFields() {
        curatorId = curator?.id
        studentId = student?.id
        subjectId = subject.id
        setSkillsIds()
    }
}