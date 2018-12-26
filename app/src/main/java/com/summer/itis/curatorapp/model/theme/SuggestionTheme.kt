package com.summer.itis.curatorapp.model.theme

import com.google.gson.annotations.SerializedName
import com.summer.itis.curatorapp.model.user.Curator
import com.summer.itis.curatorapp.model.user.Student
import com.summer.itis.curatorapp.utils.Const.STUDENT_TYPE
import com.summer.itis.curatorapp.utils.Const.WAITING_CURATOR
import com.summer.itis.curatorapp.model.common.Identified
import java.util.*

class SuggestionTheme: Identified {

    lateinit override var id: String

    var curator: Curator? = null
    var student: Student? = null
    var themeProgress: ThemeProgress? = null
    var theme: Theme? = null
    var status: Status = Status(Integer.toString(Random().nextInt(100) + 1), WAITING_CURATOR)
    @SerializedName("theme_id")
    var themeId: String? = theme?.id
    @SerializedName("curator_id")
    var curatorId: String? = curator?.id
    @SerializedName("student_id")
    var studentId: String? = student?.id
    @SerializedName("status_id")
    var statusId: String? = status?.id
    lateinit var themeProgressId: String
    @SerializedName("date_creation")
    lateinit var dateCreation: Date
    @SerializedName("progress_id")
    lateinit var progressId: String
    var type: String = STUDENT_TYPE

    fun setApiFileds() {
        themeId = theme?.id
        curatorId = curator?.id
        studentId = student?.id
        statusId = status?.id
    }

}