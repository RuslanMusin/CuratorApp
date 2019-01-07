package com.summer.itis.curatorapp.model.user

import com.google.gson.annotations.SerializedName

class Student: User {

    constructor()

    constructor(email: String, username: String): super(email, username)

    @SerializedName("course_number")
    var course: Long = 1

    lateinit var group: Group



}