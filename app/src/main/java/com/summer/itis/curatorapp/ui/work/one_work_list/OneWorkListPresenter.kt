package com.summer.itis.curatorapp.ui.work.one_work_list

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.summer.itis.curatorapp.model.work.Work
import com.summer.itis.curatorapp.repository.RepositoryProvider
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragPresenter
import com.summer.itis.curatorapp.utils.Const
import io.reactivex.disposables.CompositeDisposable

@InjectViewState
class OneWorkListPresenter(): BaseFragPresenter<OneWorkListView>() {

    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun loadWorks(userId: String) {
        Log.d(Const.TAG_LOG, "id = $userId")
        val disposable = RepositoryProvider.worksRepository.findStudentWorks(userId).subscribe { res ->
            interceptSecondResponse(res, showWorks(),
                { loadWorks(userId)})
        }
        compositeDisposable.add(disposable)
    }

    private fun showWorks(): (works: List<Work>) -> Unit {
        return {
            viewState.showWorks(it)
        }
    }

}