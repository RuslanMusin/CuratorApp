package com.summer.itis.curatorapp.model.api_result

import com.google.gson.annotations.SerializedName

class LoginResult {
    @SerializedName("token")
    lateinit var token: String
    @SerializedName("user_id")
    lateinit var userId: String
}