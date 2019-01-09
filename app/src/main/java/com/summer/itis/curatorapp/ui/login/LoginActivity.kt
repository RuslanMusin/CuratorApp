package com.summer.itis.curatorapp.ui.login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.crashlytics.android.Crashlytics
import com.summer.itis.curatorapp.R
import com.summer.itis.curatorapp.model.user.Curator
import com.summer.itis.curatorapp.ui.base.base_first.activity.BaseActivity
import com.summer.itis.curatorapp.ui.base.navigation_base.NavigationBaseActivity
import com.summer.itis.curatorapp.utils.Const.USER_DATA_PREFERENCES
import com.summer.itis.curatorapp.utils.Const.USER_PASSWORD
import com.summer.itis.curatorapp.utils.Const.USER_USERNAME
import io.fabric.sdk.android.Fabric
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity<LoginPresenter>(), LoginActView, View.OnClickListener {

    @InjectPresenter
    override lateinit var presenter: LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Fabric.with(this, Crashlytics())
        setContentView(R.layout.activity_login)
        setListeners()
        checkUserSession()
    }

    private fun checkUserSession() {
        val sharedPreferences: SharedPreferences = getSharedPreferences(USER_DATA_PREFERENCES, Context.MODE_PRIVATE)
        if(sharedPreferences.contains(USER_USERNAME)) {
            val email: String = sharedPreferences.getString(USER_USERNAME,"")
            val password: String = sharedPreferences.getString(USER_PASSWORD,"")
            presenter.signIn(email,password)
        }
    }

    override fun showEmailError(hasError: Boolean) {
        if(hasError) {
            ti_email.error = getString(R.string.enter_correct_name)
        } else {
            ti_email.error = null
        }

    }

    override fun showPasswordError(hasError: Boolean) {
        if(hasError) {
            ti_password.error = getString(R.string.enter_correct_password)
        } else {
            ti_password.error = null
        }

    }

    private fun setListeners() {
        btn_enter.setOnClickListener(this)
        iv_cover.setOnClickListener(this)
        tv_name.setOnClickListener(this)
    }

    override fun onClick(view: View) {

        when (view.id) {

            R.id.btn_enter -> {
                val username = et_email.getText().toString();
                val password = et_password.getText().toString();
                presenter.signIn(username, password)
            }

            R.id.tv_name -> {
                et_email.setText("Феликс.Туполев.Ипатович")
                et_password.setText("Феликс.Туполев.Ипатович")

            }

            R.id.iv_cover -> {
                et_email.setText("Прохор.Куклев.Брониславович")
                et_password.setText("Прохор.Куклев.Брониславович")
            }
        }
    }

    override fun goToProfile(curator: Curator) {
        NavigationBaseActivity.start(this)
    }

    override fun showError() {
        ti_email.error = getString(R.string.enter_correct_name)
        ti_password.error = getString(R.string.enter_correct_password)
    }

    override fun createCookie(email: String, password: String) {
        val mySharedPreferences = getSharedPreferences(USER_DATA_PREFERENCES, Context.MODE_PRIVATE)
        if(!mySharedPreferences.contains(USER_USERNAME)) {
            val editor = mySharedPreferences.edit()
            editor.putString(USER_USERNAME, email)
            editor.putString(USER_PASSWORD, password)
            editor.apply()
        }
    }

    companion object {

        fun start(activity: Context) {
            val intent = Intent(activity, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            activity.startActivity(intent)
        }
    }
}
