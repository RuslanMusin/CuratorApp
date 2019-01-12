package com.summer.itis.curatorapp.model.help

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.summer.itis.curatorapp.model.theme.Suggestion
import java.util.*


class SuggestionApi {

    @Expose(serialize = false)
    lateinit var id: String

    @SerializedName("curator")
    var curatorId: String? = null
    @SerializedName("student")
    var studentId: String? = null
    @SerializedName("theme")
    var themeId: String? = null

    @SerializedName("date_creation")
    var dateCreation: Date? = null
    @SerializedName("status")
    var status: String? = null

    constructor()

    constructor(suggestion: Suggestion) {
        this.id = suggestion.id
        this.curatorId = suggestion.curator?.id
        this.studentId = suggestion.student?.id
        this.themeId = suggestion.theme?.id
        this.dateCreation = suggestion.dateCreation
        this.status = suggestion.status.id
    }
}