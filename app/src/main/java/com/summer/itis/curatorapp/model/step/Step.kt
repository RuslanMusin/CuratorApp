package com.summer.itis.curatorapp.model.step

import com.summer.itis.curatorapp.model.common.Identified
import com.summer.itis.curatorapp.model.theme.Theme
import java.util.*

class Step: Identified {

    override lateinit var id: String

    lateinit var title: String
    lateinit var description: String
    lateinit var links: String

    lateinit var dateStart: Date
    lateinit var dateFinish: Date
}