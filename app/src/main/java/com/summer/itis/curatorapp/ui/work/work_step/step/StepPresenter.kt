package com.summer.itis.curatorapp.ui.work.work_step.step

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.summer.itis.curatorapp.repository.RepositoryProvider
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragPresenter
import com.summer.itis.curatorapp.utils.Const
import io.reactivex.disposables.CompositeDisposable

@InjectViewState
class  StepPresenter(): BaseFragPresenter<StepView>() {

    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun loadStep(workId: String, stepId: String) {
        val disposable = RepositoryProvider.workStepRepository.findById(workId, stepId).subscribe { res ->
            Log.d(Const.TAG_LOG, "receive work response")
            if(res == null) {
                Log.d(Const.TAG_LOG, "work res == null")
            } else {
                if(res.response() == null) {
                    Log.d(Const.TAG_LOG, "work response == null and isError = ${res.isError}")
                    Log.d(Const.TAG_LOG, "work error = ${res.error()?.message}")
                    res.error()?.printStackTrace()
                }
            }
            res?.response()?.let {
                if (it.isSuccessful) {
                    Log.d(Const.TAG_LOG, "successful work")
                    it.body()?.let { step ->
                        viewState.showStep(step)
                    }
                } else {
                    Log.d(Const.TAG_LOG, "failed get step = ${it.code()} and ${it.errorBody()?.string()} and ${it.message()}")
                    Log.d(Const.TAG_LOG, "failed raw = ${it.raw()}")
                }
            }
        }
        compositeDisposable.add(disposable)
    }

}