package com.summer.itis.curatorapp.ui.work.work_step.edit_step

import android.content.Intent
import com.arellomobile.mvp.InjectViewState
import com.summer.itis.curatorapp.R
import com.summer.itis.curatorapp.model.step.Step
import com.summer.itis.curatorapp.repository.RepositoryProvider
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragPresenter
import com.summer.itis.curatorapp.utils.AppHelper
import com.summer.itis.curatorapp.utils.Const
import io.reactivex.disposables.CompositeDisposable


@InjectViewState
class EditStepPresenter(): BaseFragPresenter<EditStepView>() {

    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun updateStep(workId: String, step: Step) {
        step.setApiFields()
        viewState.startTimeout ( R.string.failed_update_step)
        val disposable = RepositoryProvider.workStepRepository
            .updateCuratorWorkStep(AppHelper.currentCurator.id, workId, step)
            .subscribe { res ->
                interceptSecondResponse(res, handleStep(),
                    R.string.failed_update_step
                    )
            }
        compositeDisposable.add(disposable)

    }

    private fun handleStep(): (step: Step) -> Unit {
        return {
            viewState.stopTimeout()
            val intent = Intent()
            intent.putExtra(Const.STEP_KEY, Const.gsonConverter.toJson(it))
            viewState.backAfterEdit(intent)
        }
    }
}