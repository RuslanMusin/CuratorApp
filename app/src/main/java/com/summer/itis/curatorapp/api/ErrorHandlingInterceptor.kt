package com.summer.itis.curatorapp.api

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException


class ErrorHandlingInterceptor private constructor() : Interceptor {

    companion object {

        fun create(): Interceptor {
            return ErrorHandlingInterceptor()
        }
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        // todo deal with the issues the way you need to
        when (response.code()) {

            500 -> showError()

            401 -> showLoginPage()

            200 -> return response

        }

        return response
    }

    private fun showError() {

    }

    private fun showLoginPage() {

    }
}
