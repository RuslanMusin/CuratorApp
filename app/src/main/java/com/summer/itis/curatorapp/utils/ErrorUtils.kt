package com.summer.itis.curatorapp.utils

import com.summer.itis.curatorapp.api.ApiFactory.Companion.errorConverter
import com.summer.itis.curatorapp.model.common.APIError
import retrofit2.Response
import java.io.IOException


object ErrorUtils {

    fun parseError(response: Response<*>): APIError {

        var error = APIError()

        try {
            response.errorBody()?.let { error = errorConverter.convert(it) }
        } catch (e: IOException) {
            return APIError()
        }

        return error
    }
}