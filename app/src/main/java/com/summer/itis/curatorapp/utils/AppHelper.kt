package com.summer.itis.curatorapp.utils

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.summer.itis.curatorapp.R
import com.summer.itis.curatorapp.model.skill.Skill
import com.summer.itis.curatorapp.model.skill.Subject
import com.summer.itis.curatorapp.model.theme.Status
import com.summer.itis.curatorapp.model.theme.Theme
import com.summer.itis.curatorapp.model.user.Curator
import com.summer.itis.curatorapp.model.user.User
import com.summer.itis.curatorapp.model.user.Student
import com.summer.itis.curatorapp.utils.Const.ACCEPTED_BOTH
import com.summer.itis.curatorapp.utils.Const.ACCEPTED_BOTH_NUM
import com.summer.itis.curatorapp.utils.Const.CHANGED_CURATOR
import com.summer.itis.curatorapp.utils.Const.CHANGED_CURATOR_NUM
import com.summer.itis.curatorapp.utils.Const.CHANGED_STUDENT
import com.summer.itis.curatorapp.utils.Const.CHANGED_STUDENT_NUM
import com.summer.itis.curatorapp.utils.Const.IN_PROGRESS_CURATOR
import com.summer.itis.curatorapp.utils.Const.IN_PROGRESS_CURATOR_NUM
import com.summer.itis.curatorapp.utils.Const.IN_PROGRESS_STUDENT
import com.summer.itis.curatorapp.utils.Const.IN_PROGRESS_STUDENT_NUM
import com.summer.itis.curatorapp.utils.Const.MAX_LENGTH
import com.summer.itis.curatorapp.utils.Const.MORE_TEXT
import com.summer.itis.curatorapp.utils.Const.OFFLINE_STATUS
import com.summer.itis.curatorapp.utils.Const.REJECTED_CURATOR
import com.summer.itis.curatorapp.utils.Const.REJECTED_CURATOR_NUM
import com.summer.itis.curatorapp.utils.Const.REJECTED_STUDENT
import com.summer.itis.curatorapp.utils.Const.REJECTED_STUDENT_NUM
import com.summer.itis.curatorapp.utils.Const.TAG_LOG
import com.summer.itis.curatorapp.utils.Const.USER_KEY
import com.summer.itis.curatorapp.utils.Const.WAITING_CURATOR
import com.summer.itis.curatorapp.utils.Const.WAITING_CURATOR_NUM
import com.summer.itis.curatorapp.utils.Const.WAITING_STUDENT
import com.summer.itis.curatorapp.utils.Const.WAITING_STUDENT_NUM
import com.summer.itis.curatorapp.utils.Const.gsonConverter
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

//ОСНОВНОЙ КЛАСС HELPER приложения. ОТСЮДА БЕРЕМ ТЕКУЩЕГО ЮЗЕРА ИЗ БД, ГРУЗИМ ФОТКУ ЮЗЕРА В ПРОФИЛЬ,
//ПОЛУЧАЕМ ССЫЛКУ НА ПУТЬ ФАЙЛОГО ХРАНИЛИЩА И СОЗДАЕМ СЕССИЮ. ПОКА ТАК ПУСТЬ БУДЕТ
class AppHelper {

    companion object {

        lateinit var currentCurator: Curator

        lateinit var otherCurator: Curator

        var userInSession: Boolean = false

        var userStatus: String = OFFLINE_STATUS

        var onlineFunction: (() -> Unit)? = null

        var offlineFunction: (() -> Unit)? = null

        fun setUserPhoto(photoView: ImageView, curator: User, context: Context) {

                Glide.with(context)
                        .load(R.drawable.teacher)
                        .into(photoView)

        }

        fun getLevelStr(level: Int, context: Context): String {
            var levelStr: String = context.getString(R.string.low_level)
            when(level) {
                0 -> levelStr = context.getString(R.string.low_level)

                1 -> levelStr = context.getString(R.string.medium_level)

                2 -> levelStr = context.getString(R.string.high_level)
            }
            return levelStr
        }

        fun getLevelInt(levelStr: String, context: Context): Int {
            var level: Int = 0
            when(levelStr) {
                context.getString(R.string.low_level) -> level = 0

                context.getString(R.string.medium_level) -> level = 1

                context.getString(R.string.high_level) -> level = 2
            }
            return level
        }

        fun hideKeyboardFrom(context: Context, view: View) {
            Log.d(TAG_LOG,"hide keyboard")
            val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0)
        }

        fun showKeyboard(context: Context, editText: EditText) {
            val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
        }

        fun cutLongDescription(description: String, maxLength: Int): String {
            return if (description.length < MAX_LENGTH) {
                description
            } else {
                description.substring(0, MAX_LENGTH - MORE_TEXT.length) + MORE_TEXT
            }
        }

        fun getStatus(statusStr: String): Status {
            var status = Status()
            when (statusStr) {

                WAITING_CURATOR -> status = Status(WAITING_CURATOR_NUM, statusStr)
                WAITING_STUDENT -> status = Status(WAITING_STUDENT_NUM, statusStr)
                IN_PROGRESS_CURATOR -> status = Status(IN_PROGRESS_CURATOR_NUM, statusStr)
                IN_PROGRESS_STUDENT -> status = Status(IN_PROGRESS_STUDENT_NUM, statusStr)
                CHANGED_CURATOR -> status = Status(CHANGED_CURATOR_NUM, statusStr)
                CHANGED_STUDENT -> status = Status(CHANGED_STUDENT_NUM, statusStr)
                REJECTED_CURATOR -> status = Status(REJECTED_CURATOR_NUM, statusStr)
                REJECTED_STUDENT -> status = Status(REJECTED_STUDENT_NUM, statusStr)
                ACCEPTED_BOTH -> status = Status(ACCEPTED_BOTH_NUM, statusStr)
            }
            return status
        }

    }

}
