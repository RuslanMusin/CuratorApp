package com.summer.itis.curatorapp.ui.work.work_step.add_step

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.summer.itis.curatorapp.model.step.Step
import com.summer.itis.curatorapp.repository.RepositoryProvider
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragPresenter
import com.summer.itis.curatorapp.utils.AppHelper
import com.summer.itis.curatorapp.utils.Const
import io.reactivex.disposables.CompositeDisposable

@InjectViewState
class AddStepPresenter(): BaseFragPresenter<AddStepView>() {

    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun addStep(workId: String, step: Step) {
        step.setApiFields()
        val disposable = RepositoryProvider.workStepRepository
            .postCuratorWorkStep(AppHelper.currentCurator.id, workId, step).subscribe { res ->
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
                        viewState.backAfterAdd()
                    }
                } else {
                    Log.d(Const.TAG_LOG, "failed add step = ${it.code()} and ${it.errorBody()?.string()} and ${it.message()}")
                    Log.d(Const.TAG_LOG, "failed raw = ${it.raw()}")
                }
            }
        }
        compositeDisposable.add(disposable)
    }

   /* fun addStep(step: Step, workId: String, context: Context) {

        val works = AppHelper.currentCurator.works
        for(work in works) {
            if(work.id.equals(workId)) {
                work.steps.add(step)
            }
        }
        AppHelper.saveCurrentState(AppHelper.currentCurator, context)
        viewState.backAfterAdd()
    }*/
}