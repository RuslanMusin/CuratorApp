package com.summer.itis.curatorapp.model.step

import com.google.gson.annotations.SerializedName
import com.summer.itis.curatorapp.model.common.Identified
import com.summer.itis.curatorapp.model.theme.Status
import com.summer.itis.curatorapp.model.theme.Theme
import java.util.*

class Step: Identified {

    override lateinit var id: String

    lateinit var title: String
    lateinit var description: String
    var links: String = "some links"

    @SerializedName("date_start")
    lateinit var dateStart: Date
    @SerializedName("date_finish")
    lateinit var dateFinish: Date

    lateinit var status: Status

    @SerializedName("status_id")
    var statusId: String? = null

    fun setApiFields() {
        statusId = status.id
    }

}