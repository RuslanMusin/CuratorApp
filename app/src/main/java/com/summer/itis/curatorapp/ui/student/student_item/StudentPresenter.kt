package com.summer.itis.curatorapp.ui.student.student_item

import com.arellomobile.mvp.InjectViewState
import com.summer.itis.curatorapp.repository.RepositoryProvider
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragPresenter
import io.reactivex.disposables.CompositeDisposable

@InjectViewState
class StudentPresenter(): BaseFragPresenter<StudentView>() {

    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun loadStudent(studentId: String) {
        val disposable = RepositoryProvider.studentRepository.findById(studentId).subscribe { res ->
            interceptSecondResponse(res, {
                viewState.showStudent(it)
            },{ loadStudent(studentId) })
        }
        compositeDisposable.add(disposable)
    }

}