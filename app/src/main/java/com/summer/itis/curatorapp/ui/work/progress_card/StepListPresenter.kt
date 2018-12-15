package com.summer.itis.curatorapp.ui.work.progress_card

import com.arellomobile.mvp.InjectViewState
import com.summer.itis.curatorapp.repository.RepositoryProvider.Companion.worksRepository
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragPresenter
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer

@InjectViewState
class StepListPresenter(): BaseFragPresenter<StepListView>() {

    val compositeDisposable: CompositeDisposable = CompositeDisposable()

}