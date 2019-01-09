package com.summer.itis.curatorapp.ui.work.work_item

import com.arellomobile.mvp.InjectViewState
import com.summer.itis.curatorapp.repository.RepositoryProvider
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragPresenter
import io.reactivex.disposables.CompositeDisposable

@InjectViewState
class  WorkPresenter(): BaseFragPresenter<WorkView>() {

    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun loadWork(workId: String) {
        val disposable = RepositoryProvider.worksRepository.findById(workId).subscribe { res ->
            interceptSecondResponse(res, { viewState.showWork(it) },{ loadWork(workId)})
        }
        compositeDisposable.add(disposable)
    }

}