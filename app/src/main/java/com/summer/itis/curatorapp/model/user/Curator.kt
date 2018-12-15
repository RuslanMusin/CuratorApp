package com.summer.itis.curatorapp.model.user

class Curator: Person {

    constructor() {}

    constructor(email: String, username: String) {
        this.email = email
        this.name = username
    }
}