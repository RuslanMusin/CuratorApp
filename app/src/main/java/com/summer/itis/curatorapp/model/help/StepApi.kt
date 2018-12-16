package com.summer.itis.curatorapp.model.help

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.summer.itis.curatorapp.model.step.Step
import java.util.*

class StepApi {

    @Expose(serialize = false)
    lateinit var id: String

    var title: String? = null
    var description: String? = null

    @SerializedName("date_start")
    var dateStart: Date? = null
    @SerializedName("date_finish")
    var dateFinish: Date? = null

    var status: String? = null

    constructor()

    constructor(step: Step) {
        this.id = step.id
        this.title = step.title
        this.description = step.description
        this.dateStart = step.dateStart
        this.dateFinish = step.dateFinish
        this.status = step.status.id
    }
}