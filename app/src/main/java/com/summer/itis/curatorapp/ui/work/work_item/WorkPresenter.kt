package com.summer.itis.curatorapp.ui.work.work_item

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.summer.itis.curatorapp.repository.RepositoryProvider
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragPresenter
import com.summer.itis.curatorapp.utils.Const
import io.reactivex.disposables.CompositeDisposable

@InjectViewState
class  WorkPresenter(): BaseFragPresenter<WorkView>() {

    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun loadWork(workId: String) {
        val disposable = RepositoryProvider.worksRepository.findById(workId).subscribe { res ->
            interceptResponse(res) { viewState.showWork(it) }
        }
        compositeDisposable.add(disposable)
    }

}