package com.summer.itis.curatorapp.ui.work.work_step.edit_step

import android.content.Context
import android.content.Intent
import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.summer.itis.curatorapp.model.help.StepApi
import com.summer.itis.curatorapp.model.step.Step
import com.summer.itis.curatorapp.repository.RepositoryProvider
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragPresenter
import com.summer.itis.curatorapp.utils.AppHelper
import com.summer.itis.curatorapp.utils.Const
import io.reactivex.disposables.CompositeDisposable


@InjectViewState
class EditStepPresenter(): BaseFragPresenter<EditStepView>() {

    val compositeDisposable: CompositeDisposable = CompositeDisposable()

  /*  fun editStep(step: Step, workId: String, context: Context) {
        val works = AppHelper.currentCurator.works
        for(work in works) {
            if(work.id.equals(workId)) {
                val steps = work.steps
                for(i in steps.indices) {
                    if(steps[i].id.equals(step.id)) {
                        steps[i] = step
                    }
                }
            }
        }
        AppHelper.saveCurrentState(AppHelper.currentCurator, context)
        val intent = Intent()
        intent.putExtra(Const.STEP_KEY, Const.gsonConverter.toJson(step))
        viewState.backAfterEdit(intent)
    }*/

    fun updateStep(workId: String, step: Step) {
        val stepApi = StepApi(step)
        val disposable = RepositoryProvider.workStepRepository
            .updateCuratorWorkStep(AppHelper.currentCurator.id, workId, stepApi).subscribe { res ->
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
                            val intent = Intent()
                            intent.putExtra(Const.STEP_KEY, Const.gsonConverter.toJson(step))
                            viewState.backAfterEdit(intent)                        }
                    } else {
                        Log.d(Const.TAG_LOG, "failed edit step = ${it.code()} and ${it.errorBody()?.string()} and ${it.message()}")
                        Log.d(Const.TAG_LOG, "failed raw = ${it.raw()}")
                    }
                }
            }
        compositeDisposable.add(disposable)
    }
}