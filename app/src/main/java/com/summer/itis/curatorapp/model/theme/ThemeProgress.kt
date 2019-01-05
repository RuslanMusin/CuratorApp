package com.summer.itis.curatorapp.model.theme

import com.google.gson.annotations.SerializedName
import com.summer.itis.curatorapp.model.common.Identified
import java.util.*

class ThemeProgress: Identified {

    lateinit override var id: String
    @SerializedName("date_update")
    lateinit var dateUpdate: Date
    lateinit var title: String
    lateinit var description: String
}