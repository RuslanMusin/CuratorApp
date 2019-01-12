package com.summer.itis.curatorapp.utils

import com.google.gson.Gson

//обычный класс констант и прочего общего кода
object Const {

    const val TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"

    const val REQUEST_CODE = "REQUEST_CODE"

    //REQUEST_CODE
    const val ADD_SUBJECT: Int = 1
    const val ADD_STUDENT: Int = 2
    const val EDIT_SUGGESTION = 3
    const val EDIT_THEME = 4
    const val EDIT_STEP = 5
    const val ADD_SKILL = 6
    const val CHOOSE_SKILL = 7
    const val CHOOSE_LEVEL = 8
    const val EDIT_CHOOSED_SKILLS = 9
    const val ADD_MATERIAL = 10
    const val EDIT_CURATOR = 11
    const val SEND_THEME = 100
    const val FILTERS = 101

    const val ADD_THEME_TYPE = "ADD_THEME"

    const val TAG_LOG = "TAG_LOG"

    const val TAB_NAME = "TAB_NAME"

    const val MAX_LENGTH = 50
    const val MORE_TEXT = "..."

    const val SPACE = " "
    const val OFFLINE_STATUS = "offline"

    //suggestion_theme_status
    const val WAITING_CURATOR = "WAITING_CURATOR"
    const val WAITING_STUDENT = "WAITING_STUDENT"
    const val IN_PROGRESS_CURATOR = "IN_PROGRESS_CURATOR"
    const val IN_PROGRESS_STUDENT = "IN_PROGRESS_STUDENT"
    const val CHANGED_CURATOR = "CHANGED_CURATOR"
    const val CHANGED_STUDENT = "CHANGED_STUDENT"
    const val REJECTED_CURATOR = "REJECTED_CURATOR"
    const val REJECTED_STUDENT = "REJECTED_STUDENT"
    const val ACCEPTED_CURATOR = "ACCEPTED_CURATOR"
    const val ACCEPTED_STUDENT = "ACCEPTED_STUDENT"
    const val ACCEPTED_BOTH = "ACCEPTED_BOTH"

    //suggestion_theme_status_num
    const val WAITING_CURATOR_NUM = "1"
    const val WAITING_STUDENT_NUM = "2"
    const val IN_PROGRESS_CURATOR_NUM = "3"
    const val IN_PROGRESS_STUDENT_NUM = "4"
    const val CHANGED_CURATOR_NUM = "5"
    const val CHANGED_STUDENT_NUM = "6"
    const val REJECTED_CURATOR_NUM = "7"
    const val REJECTED_STUDENT_NUM = "8"
    const val ACCEPTED_BOTH_NUM = "9"
    const val ACCEPTED_CURATOR_NUM = "10"
    const val ACCEPTED_STUDENT_NUM = "11"

    //step status_num
    const val NOT_DONE = "1"
    const val IN_PROCESS = "2"
    const val DONE = "3"

    //Intent keys
    const val USER_ID = "user_id"
    const val ID_KEY = "id"
    const val USER_KEY = "step"
    const val CURATOR_KEY = "curator"
    const val STUDENT_KEY = "student"
    const val SUBJECT_KEY = "subject"
    const val SKILL_KEY = "skill"
    const val THEME_KEY = "suggestion"
    const val COURSE_KEY = "course_key"
    const val SUGGESTION_KEY = "suggestion_key"
    const val WORK_KEY = "work_key"
    const val STEP_KEY = "step_key"
    const val MATERIAL_KEY = "material_key"
    const val DESC_KEY = "desc"

    //Watch type
    const val WATCHER_TYPE = "watcher"
    const val OWNER_TYPE = "owner"

    //Types
    const val TYPE = "TYPE"

    const val PERSON_TYPE = "PERSON_TYPE"
    const val CURATOR_TYPE = "CURATOR_TYPE"
    const val STUDENT_TYPE = "STUDENT_TYPE"
    const val SUGGESTION_TYPE = "SUGGESTION_TYPE"
    const val STEP_TYPE = "STEP_TYPE"
    const val THEME_TYPE = "THEME_TYPE"

    const val ALL_CHOOSED = "all_choosed"
    const val ONE_CHOOSED = "one_choosed"

    //SharedPreferences
    const val USER_DATA_PREFERENCES = "user_data"
    const val USER_USERNAME = "user_username"
    const val USER_PASSWORD = "user_password"

    const val ENTITY_NOT_EXIST = "not_exist"

    // Http request
    const val AUTHORIZATION = "Authorization"
    const val CONTENT_TYPE = "Content-Type"
    const val ACCEPT = "Accept"
    const val CSRF_TOKEN = "X-CSRFToken"
    const val APP_JSON = "application/json"
    const val CSRF_TOKEN_VALUE = "l35FZP6OxS5ZgIpT1f3Qmv6h36N7qRptcSjAMZk6pebCUU7wm15YK3xLAZC9OcRj"
    var AUTH_VALUE = "Token dsfasd"

    //Theme list types
    const val SUGGESTIONS_LIST = "SUGGESTIONS"
    const val MY_THEMES_LIST = "MY_THEMES"

    @JvmField
    val gsonConverter = Gson()
}
