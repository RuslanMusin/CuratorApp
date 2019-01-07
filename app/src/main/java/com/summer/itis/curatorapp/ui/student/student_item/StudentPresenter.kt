package com.summer.itis.curatorapp.ui.student.student_item

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.summer.itis.curatorapp.repository.RepositoryProvider
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragPresenter
import com.summer.itis.curatorapp.utils.Const
import io.reactivex.disposables.CompositeDisposable

@InjectViewState
class StudentPresenter(): BaseFragPresenter<StudentView>() {

    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun loadStudent(studentId: String) {
        val disposable = RepositoryProvider.studentRepository.findById(studentId).subscribe { res ->
            interceptResponse(res) {
                viewState.showStudent(it)
            }
        }
        compositeDisposable.add(disposable)
    }

}