package com.summer.itis.curatorapp.ui.work.work_list.tab_fragment.past_work_list

import com.arellomobile.mvp.InjectViewState
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragPresenter
import io.reactivex.disposables.CompositeDisposable

@InjectViewState
class PastWorkListPresenter(): BaseFragPresenter<PastWorkListView>() {

    val compositeDisposable: CompositeDisposable = CompositeDisposable()

}