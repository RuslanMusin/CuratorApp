package com.summer.itis.curatorapp.api

import com.summer.itis.curatorapp.utils.Const.ACCEPT
import com.summer.itis.curatorapp.utils.Const.APP_JSON
import com.summer.itis.curatorapp.utils.Const.AUTHORIZATION
import com.summer.itis.curatorapp.utils.Const.AUTH_VALUE
import com.summer.itis.curatorapp.utils.Const.AUTH_VALUE_2
import com.summer.itis.curatorapp.utils.Const.CONTENT_TYPE
import com.summer.itis.curatorapp.utils.Const.CSRF_TOKEN
import com.summer.itis.curatorapp.utils.Const.CSRF_TOKEN_VALUE
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class ApiKeyInterceptor private constructor() : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        return chain.proceed(
            original.newBuilder()
//                .addHeader(CONTENT_TYPE, APP_JSON)
//                .addHeader(CSRF_TOKEN, CSRF_TOKEN_VALUE)
                .addHeader(ACCEPT, APP_JSON)
                .addHeader(AUTHORIZATION, AUTH_VALUE_2)
                .build()
        )
    }

    companion object {

        fun create(): Interceptor {
            return ApiKeyInterceptor()
        }
    }
}