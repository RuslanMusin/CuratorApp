package com.summer.itis.curatorapp.ui.work.progress_card

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.summer.itis.curatorapp.model.step.Step
import com.summer.itis.curatorapp.repository.RepositoryProvider
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragPresenter
import com.summer.itis.curatorapp.utils.Const
import io.reactivex.disposables.CompositeDisposable

@InjectViewState
class StepListPresenter(): BaseFragPresenter<StepListView>() {

    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun loadSteps(workId: String) {
        val disposable = RepositoryProvider.workStepRepository.findAll(workId).subscribe { res ->
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
                    it.body()?.let { steps ->
                        viewState.showSteps(steps.sortedWith(compareBy (Step::dateFinish)))
                    }
                } else {
                    Log.d(Const.TAG_LOG, "failed get steps = ${it.code()} and ${it.errorBody()?.string()} and ${it.message()}")
                    Log.d(Const.TAG_LOG, "failed raw = ${it.raw()}")
                }
            }
        }
        compositeDisposable.add(disposable)
    }

}