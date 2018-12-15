package com.summer.itis.curatorapp.model.work

import com.summer.itis.curatorapp.model.theme.Theme
import com.summer.itis.curatorapp.model.common.Identified
import com.summer.itis.curatorapp.model.step.Step
import java.util.*

class Work: Identified {

    override lateinit var id: String

    lateinit var theme: Theme

    lateinit var dateStart: Date
    var dateFinish: Date? = null

    var steps: MutableList<Step> = ArrayList()
}