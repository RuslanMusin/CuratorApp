package com.summer.itis.curatorapp.model.help

import com.google.gson.annotations.SerializedName
import com.summer.itis.curatorapp.model.user.Curator
import java.util.*

class CuratorApi {

    var name: String? = null
    @SerializedName("last_name")
    var lastname: String? = null
    var patronymic: String? = null

    var description: String? = null
    @SerializedName("skills_id")
    var skillsIds: MutableList<String> = ArrayList()

    constructor()

    constructor(curator: Curator) {
        this.name = curator.name
        this.lastname = curator.lastname
        this.patronymic = curator.patronymic
        this.description = curator.description
        for(skill in curator.skills) {
            this.skillsIds.add(skill.id)
        }
    }
}