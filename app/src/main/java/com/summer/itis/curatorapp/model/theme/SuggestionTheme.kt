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

    lateinit var themeId: String
    lateinit var curatorId: String
    lateinit var studentId: String
    lateinit var themeProgressId: String
    var status: Status = Status(Integer.toString(Random().nextInt(100) + 1), WAITING_CURATOR)
    @SerializedName("date_creation")
    lateinit var dateCreation: Date

    lateinit var progress: String
    var type: String = STUDENT_TYPE

    var curator: Curator? = null
    var student: Student? = null
    var themeProgress: ThemeProgress? = null
    var theme: Theme? = null
}