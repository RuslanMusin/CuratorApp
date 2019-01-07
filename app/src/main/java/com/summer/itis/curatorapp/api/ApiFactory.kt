package com.summer.itis.curatorapp.api

import com.google.gson.GsonBuilder
import com.summer.itis.curatorapp.BuildConfig
import com.summer.itis.curatorapp.api.services.*
import com.summer.itis.curatorapp.model.common.APIError
import com.summer.itis.curatorapp.utils.Const.TIME_FORMAT
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class ApiFactory {

    companion object {

        private fun buildRetrofit(): Retrofit {
            val gson = GsonBuilder().setDateFormat(TIME_FORMAT).create()

            return Retrofit.Builder()
                    .baseUrl(BuildConfig.API_ENDPOINT)
                    .client(OkHttpProvider.provideClient())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
        }

        private fun buildLoginRetrofit(): Retrofit {

            return Retrofit.Builder()
                .baseUrl(BuildConfig.API_ENDPOINT)
                .client(OkHttpProvider.provideLoginClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        }

        val authService: AuthService by lazy {
            buildLoginRetrofit().create(AuthService::class.java)
        }

        val curatorService: CuratorService by lazy {
            buildRetrofit().create(CuratorService::class.java)
        }

        val studentService: StudentService by lazy {
            buildRetrofit().create(StudentService::class.java)
        }

        val skillService: SkillService by lazy {
            buildRetrofit().create(SkillService::class.java)
        }

        val subjectService: SubjectService by lazy {
            buildRetrofit().create(SubjectService::class.java)
        }

        val themeService: ThemeService by lazy {
            buildRetrofit().create(ThemeService::class.java)
        }

        val suggestionService: SuggestionService by lazy {
            buildRetrofit().create(SuggestionService::class.java)
        }

        val workService: WorkService by lazy {
            buildRetrofit().create(WorkService::class.java)
        }

        val workStepService: WorkStepService by lazy {
            buildRetrofit().create(WorkStepService::class.java)
        }

        val materialService: MaterialService by lazy {
            buildRetrofit().create(MaterialService::class.java)
        }

        val commentService: CommentService by lazy {
            buildRetrofit().create(CommentService::class.java)
        }

        val errorConverter: Converter<ResponseBody, APIError> by lazy {
            val converter: Converter<ResponseBody, APIError> = ApiFactory.buildRetrofit()
                    .responseBodyConverter(APIError::class.java, arrayOfNulls<Annotation>(0))

            converter
        }

    }

}
