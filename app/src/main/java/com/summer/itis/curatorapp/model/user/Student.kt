package com.summer.itis.curatorapp.model.user

class Student: Person {

    var year: Long = 1
    var groupNumber: String = "11-603"
    constructor() {}

    constructor(email: String, username: String) {
        this.email = email
        this.name = username
    }
}