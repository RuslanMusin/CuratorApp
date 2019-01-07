package com.summer.itis.curatorapp.model.user

import com.summer.itis.curatorapp.model.common.Identified

class Group: Identified {

    override lateinit var id: String
    var name: String = "11-603"

    constructor() {}

}