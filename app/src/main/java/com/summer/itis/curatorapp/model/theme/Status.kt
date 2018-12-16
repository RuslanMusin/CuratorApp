package com.summer.itis.curatorapp.model.theme

import com.summer.itis.curatorapp.model.common.Identified

class Status: Identified {
    override lateinit var id: String
    lateinit var name: String

    constructor()

    constructor(id: String, name: String) {
        this.id = id
        this.name = name
    }


}