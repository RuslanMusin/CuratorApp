package com.summer.itis.curatorapp.model.skill

import com.summer.itis.curatorapp.model.common.Identified

class Subject: Identified {

    lateinit override var id: String
    lateinit var name: String

    constructor(id: String, name: String) {
        this.id = id
        this.name = name
    }

    constructor()

}