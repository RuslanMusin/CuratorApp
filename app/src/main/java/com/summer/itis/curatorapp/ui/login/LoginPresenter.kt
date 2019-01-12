package com.summer.itis.curatorapp.ui.login

import android.text.TextUtils
import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.summer.itis.curatorapp.R
import com.summer.itis.curatorapp.model.user.Curator
import com.summer.itis.curatorapp.repository.RepositoryProvider.Companion.authRepository
import com.summer.itis.curatorapp.repository.RepositoryProvider.Companion.curatorRepository
import com.summer.itis.curatorapp.ui.base.base_first.activity.BasePresenter
import com.summer.itis.curatorapp.utils.AppHelper
import com.summer.itis.curatorapp.utils.Const.AUTH_VALUE
import com.summer.itis.curatorapp.utils.Const.TAG_LOG
import com.summer.itis.curatorapp.utils.ErrorUtils
import io.reactivex.disposables.CompositeDisposable


@InjectViewState
class LoginPresenter: BasePresenter<LoginActView>() {

    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun signIn(email: String, password: String) {
        Log.d(TAG_LOG, "signIn:$email")
        if (!validateForm(email, password)) {
            return
        }

        viewState.showProgressDialog(R.string.progress_message)

        val curator = Curator(email, password)

        val disposable = authRepository.login(curator).subscribe { res ->
            val response = res?.response()
            if(response == null) {
                Log.d(TAG_LOG, "login response is null")
                Log.d(TAG_LOG, "error = ${res?.error()?.message}")
                viewState.showSnackBar(R.string.connection_reset)
                viewState.hideProgressDialog()
            }
            res?.response()?.let {
                Log.d(TAG_LOG, res.response()?.body().toString())
               if(it.isSuccessful) {
                   val loginResult = it.body()
                   Log.d(TAG_LOG, "signInWithEmail:success")
                   viewState.createCookie(email, password)
                   loginResult?.let {result ->
                       AUTH_VALUE = "Token ${result.token}"
                       Log.d(TAG_LOG, "token = ${result.token} and id = ${result.userId}")
                       Log.d(TAG_LOG, "auth_value (token) = ${AUTH_VALUE}")
                       updateUI(result.userId)
                       viewState.hideProgressDialog()
                   }
               } else {
                   val error = ErrorUtils.parseError(it)
                   viewState.hideProgressDialog()
                   viewState.showSnackBar(R.string.error_authentication)
                   viewState.showError()
                   Log.d(TAG_LOG, "err message =  ${it.message()}")
               }

            }
        }
        compositeDisposable.add(disposable)
    }

    private fun validateForm(email: String, password: String): Boolean {
        return checkEmail(email) && checkPassword(password)
    }

    private fun checkEmail(email: String): Boolean {
        return if (TextUtils.isEmpty(email)) {
            viewState.showEmailError(true)
            false
        } else {
            viewState.showEmailError(false)
            true
        }
    }

    private fun checkPassword(password: String): Boolean {
        return if (TextUtils.isEmpty(password)) {
            viewState.showPasswordError(true)
            false
        } else {
            viewState.showPasswordError(false)
            true
        }
    }

    private fun updateUI(userId: String) {
        val disposable = curatorRepository.findById(userId).subscribe { res ->
            res?.response()?.let {
                if(it.isSuccessful) {
                    val curator = it.body()
                    curator?.let {
                        setUserSession(curator)
                        viewState.goToProfile(curator)
                    }
                } else {
                    // parse the response body …
                    val error = ErrorUtils.parseError(it)
                    // … and use it to show error information
                    viewState.showSnackBar(R.string.error_authentication)
                    viewState.showError()
                    // … or just log the issue like we’re doing :)
                    Log.d("error message", error.error)
                }
                viewState.hideProgressDialog()
            }
        }
        compositeDisposable.add(disposable)
    }

    fun setUserSession(curator: Curator) {
        AppHelper.currentCurator = curator
        AppHelper.userInSession = true
    }
}
