package com.summer.itis.curatorapp.ui.student.search.edit_choose_skill

import com.arellomobile.mvp.InjectViewState
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragPresenter
import io.reactivex.disposables.CompositeDisposable

@InjectViewState
class EditChosePreseter(): BaseFragPresenter<EditChooseView>() {

    val compositeDisposable: CompositeDisposable = CompositeDisposable()

   /* fun loadWorks(userId: String) {
        val disposable = skillRepository
                .findMySkills(userId)
                .doOnSubscribe(Consumer<Disposable> { viewState.showLoading(it) })
                .doAfterTerminate(Action { viewState.hideLoading() })
                .subscribe ({ viewState.changeDataSet(it) }, { viewState.handleError(it) })
        compositeDisposable.add(disposable)
    }*/

}