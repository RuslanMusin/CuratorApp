package com.summer.itis.curatorapp.ui.subject.add_subject

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.summer.itis.curatorapp.repository.RepositoryProvider.Companion.subjectRepository
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragPresenter
import com.summer.itis.curatorapp.utils.Const
import com.summer.itis.curatorapp.utils.Const.TAG_LOG
import io.reactivex.disposables.CompositeDisposable

@InjectViewState
class AddSubjectPresenter(): BaseFragPresenter<AddSubjectView>() {

    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun loadSubjects() {
        val disposable = subjectRepository.findAll().subscribe { res ->
            interceptResponse(res) {
                viewState.showSubjects(it)
            }
        }
        compositeDisposable.add(disposable)
    }
}