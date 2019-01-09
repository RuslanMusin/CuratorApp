package com.summer.itis.curatorapp.ui.work.works

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.summer.itis.curatorapp.repository.RepositoryProvider
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragPresenter
import com.summer.itis.curatorapp.utils.Const
import io.reactivex.disposables.CompositeDisposable

@InjectViewState
class WorkListPresenter(): BaseFragPresenter<WorkListView>() {

    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun loadWorks(userId: String) {
        Log.d(Const.TAG_LOG, "id = $userId")
        val disposable = RepositoryProvider.worksRepository.findCuratorWorks(userId).subscribe { res ->
            interceptSecondResponse(res, { viewState.showWorks(it.reversed())},{ loadWorks(userId)})
        }
        compositeDisposable.add(disposable)
    }
}