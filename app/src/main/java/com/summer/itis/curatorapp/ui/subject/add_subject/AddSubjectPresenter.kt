package com.summer.itis.curatorapp.ui.subject.add_subject

import com.arellomobile.mvp.InjectViewState
import com.summer.itis.curatorapp.R
import com.summer.itis.curatorapp.repository.RepositoryProvider.Companion.subjectRepository
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragPresenter
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.item_work.view.*

@InjectViewState
class AddSubjectPresenter(): BaseFragPresenter<AddSubjectView>() {

    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun loadSubjects() {
        val disposable = subjectRepository.findAll().subscribe { res ->
            interceptSecondResponse(res, {
                viewState.showSubjects(it)
            }, {
                loadSubjects()
            })
        }
        compositeDisposable.add(disposable)
    }
}