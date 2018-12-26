package com.summer.itis.curatorapp.model.material

import com.google.gson.annotations.SerializedName
import com.summer.itis.curatorapp.model.common.Identified

class Material: Identified {

    lateinit override var id: String
    lateinit var content: String
    @SerializedName("step_id")
    lateinit var stepId: String

    constructor(id: String, name: String) {
        this.id = id
        this.content = name
    }

    constructor()

}