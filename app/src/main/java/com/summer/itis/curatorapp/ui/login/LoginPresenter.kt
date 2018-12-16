package com.summer.itis.curatorapp.ui.login

import android.text.TextUtils
import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.summer.itis.curatorapp.R
import com.summer.itis.curatorapp.model.api_result.LoginBody
import com.summer.itis.curatorapp.model.user.Curator
import com.summer.itis.curatorapp.repository.RepositoryProvider.Companion.authRepository
import com.summer.itis.curatorapp.repository.RepositoryProvider.Companion.curatorRepository
import com.summer.itis.curatorapp.ui.base.base_first.activity.BasePresenter
import com.summer.itis.curatorapp.ui.login.LoginActivity.Companion.TAG_LOGIN
import com.summer.itis.curatorapp.utils.AppHelper
import com.summer.itis.curatorapp.utils.Const.TAG_LOG
import io.reactivex.disposables.CompositeDisposable
import com.summer.itis.curatorapp.utils.ErrorUtils
import com.summer.itis.curatorapp.utils.Const.AUTH_VALUE_2


@InjectViewState
class LoginPresenter: BasePresenter<LoginActView>() {

    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun signIn(email: String, password: String) {
        Log.d(TAG_LOG, "signIn:$email")
        if (!validateForm(email, password)) {
            return
        }

        viewState.showProgressDialog(R.string.progress_message)

        val loginBody = LoginBody(email, password)
        val disposable = authRepository.login(loginBody).subscribe { res ->
            val response = res?.response()
            if(response == null) {
                Log.d(TAG_LOG, "login response is null")
                Log.d(TAG_LOG, "error = ${res?.error()?.message}")
            }
            res?.response()?.let {
                Log.d(TAG_LOGIN, res.response()?.body().toString())
               if(it.isSuccessful) {
                   val loginResult = it.body()
                   Log.d(TAG_LOGIN, "signInWithEmail:success")
                   viewState.createCookie(email, password)
                   loginResult?.let {result ->
                       AUTH_VALUE_2 = "Token ${result.token}"
                       Log.d(TAG_LOGIN, "token = ${result.token} and id = ${result.userId}")
                       updateUI(result.userId)
                       viewState.hideProgressDialog()
                   }
               } else {
                   // parse the response body …
                   val error = ErrorUtils.parseError(it)
                   // … and use it to show error information
                   viewState.hideProgressDialog()
                   viewState.showSnackBar(R.string.error_authentication)
                   viewState.showError()
                   // … or just log the issue like we’re doing :)
                   Log.d(TAG_LOGIN, error.error)
               }

            }
           /* res.response()?.body()?.let { loginResult ->
                if (loginResult.isSuccessful) {
                    Log.d(TAG_LOGIN, "signInWithEmail:success")
                    viewState.createCookie(email, password)
                    updateUI(loginResult.userId, email)
                } else {
                    viewState.showSnackBar(R.string.error_authentication)
                    viewState.showError()
                }
                viewState.hideProgressDialog()
            }*/
        }
        compositeDisposable.add(disposable)
      /*  val loginResult = LoginResult()
        loginResult.userId = "1"
        Log.d(TAG_LOGIN, "signInWithEmail:success")
        viewState.createCookie(email, password)
        updateUI(loginResult.userId, email)*/

    }

    fun validateForm(email: String, password: String): Boolean {
        var valid = true

        if (TextUtils.isEmpty(email)) {
            viewState.showEmailError(true)
            valid = false
        } else {
            viewState.showEmailError(false)
        }

        if (TextUtils.isEmpty(password)) {
            viewState.showPasswordError(true)
            valid = false
        } else {
            viewState.showPasswordError(false)
        }

        return valid
    }

    fun updateUI(userId: String) {
        val disposable = curatorRepository.findById(userId).subscribe { res ->
            res?.response()?.let {
                if(it.isSuccessful) {
                    val curator = it.body()
                    curator?.let {
                        curator.email = "Прохор.Куклев.Брониславович"
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
       /* val curator = Curator()
        curator.id = userId
        curator.email = email
        curator.name = "Marat"
        curator.lastname = "Nasrutdinov"
        curator.patronymic = "Faritovich"
        curator.photoUrl = STUB_PATH
        curator.description = "usual desc"
        setUserSession(curator)

        val otherCurator = Curator()
        otherCurator.id = userId + "1"
        otherCurator.email = email + "1"
        otherCurator.name = "Айрат"
        otherCurator.lastname = "Хасьянов"
        otherCurator.patronymic = "Фаридович"
        otherCurator.photoUrl = STUB_PATH
        otherCurator.description = "usual desc"
        AppHelper.otherCurator = otherCurator

        viewState.goToProfile(curator)*/
    }

    fun setUserSession(curator: Curator) {
        AppHelper.currentCurator = curator
        AppHelper.userInSession = true
//    userRepository.changeJustUserStatus(ONLINE_STATUS).subscribe()
    }
}
