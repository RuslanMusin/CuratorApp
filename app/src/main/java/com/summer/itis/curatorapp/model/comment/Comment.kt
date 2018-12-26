package com.summer.itis.curatorapp.model.comment

import com.google.gson.annotations.SerializedName
import com.summer.itis.curatorapp.model.common.Identified
import java.util.*

class Comment: Identified {

    override lateinit var id: String
    lateinit var content: String
    @SerializedName("author_name")
    lateinit var authorName: String
    @SerializedName("date_creation")
    var createdDate: Date = Date()

    @Transient
    var authorPhotoUrl: String? = null
    var authorId: String? = null

    constructor() {}

    constructor(text: String) {
        this.content = text
    }
}
