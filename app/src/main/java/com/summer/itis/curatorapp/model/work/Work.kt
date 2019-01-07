package com.summer.itis.curatorapp.model.work

import com.google.gson.annotations.SerializedName
import com.summer.itis.curatorapp.model.common.Identified
import com.summer.itis.curatorapp.model.step.Step
import com.summer.itis.curatorapp.model.theme.Theme
import java.util.*

class Work: Identified {

    override lateinit var id: String

    lateinit var theme: Theme

    @SerializedName("date_start")
    lateinit var dateStart: Date
    @SerializedName("date_finish")
    var dateFinish: Date? = null

    var steps: MutableList<Step> = ArrayList()
}