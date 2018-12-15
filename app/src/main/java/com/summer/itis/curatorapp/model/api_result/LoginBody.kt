package com.summer.itis.curatorapp.model.api_result

import com.google.gson.annotations.Expose

class LoginBody(@Expose var username: String, @Expose var password: String) {
}