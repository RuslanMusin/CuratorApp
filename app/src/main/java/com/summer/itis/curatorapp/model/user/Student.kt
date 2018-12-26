package com.summer.itis.curatorapp.model.user

import com.google.gson.annotations.SerializedName

class Student: Person {

    @SerializedName("course_number")
    var courseNumber: Long = 1

    lateinit var group: Group

    constructor() {}

    constructor(email: String, username: String) {
        this.email = email
        this.name = username
    }
}