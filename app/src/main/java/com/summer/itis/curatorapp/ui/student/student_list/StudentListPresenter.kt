package com.summer.itis.curatorapp.ui.student.student_list

import com.arellomobile.mvp.InjectViewState
import com.summer.itis.curatorapp.repository.RepositoryProvider
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragPresenter
import io.reactivex.disposables.CompositeDisposable

@InjectViewState
class StudentListPresenter(): BaseFragPresenter<StudentListView>() {

    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun loadStudents() {
        viewState.startTimeout { loadStudents() }
        val disposable = RepositoryProvider.studentRepository.findAll().subscribe { res ->
           interceptSecondResponse(res, {
               viewState.stopTimeout()
               viewState.showStudents(it)
           },
               { loadStudents() })
        }
        compositeDisposable.add(disposable)
    }

}