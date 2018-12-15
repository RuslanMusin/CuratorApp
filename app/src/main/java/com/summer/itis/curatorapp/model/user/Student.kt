package com.summer.itis.curatorapp.model.user

class Student: Person {

    var year: Long = 1
    lateinit var groupNumber: String
    constructor() {}

    constructor(email: String, username: String) {
        this.email = email
        this.name = username
    }
}