package com.summer.itis.curatorapp.ui.work.one_work_list

import com.arellomobile.mvp.InjectViewState
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragPresenter
import io.reactivex.disposables.CompositeDisposable

@InjectViewState
class OneWorkListPresenter(): BaseFragPresenter<OneWorkListView>() {

    val compositeDisposable: CompositeDisposable = CompositeDisposable()


   /* fun loadWorks(id: String, dateFinish: Long?, skill: String?) {
              val disposable = worksRepository
                      .findMyWorks(id, dateFinish, skill)
                      .doOnSubscribe(Consumer<Disposable> { viewState.showLoading(it) })
                      .doAfterTerminate(Action { viewState.hideLoading() })
                      .subscribe ({ viewState.changeDataSet(it) }, { viewState.handleError(it) })
        compositeDisposable.add(disposable)
    }*/

}