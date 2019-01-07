package com.summer.itis.curatorapp.ui.student.student_list

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.summer.itis.curatorapp.repository.RepositoryProvider
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragPresenter
import com.summer.itis.curatorapp.utils.Const
import io.reactivex.disposables.CompositeDisposable

@InjectViewState
class StudentListPresenter(): BaseFragPresenter<StudentListView>() {

    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun loadStudents() {
        val disposable = RepositoryProvider.studentRepository.findAll().subscribe { res ->
           interceptResponse(res) {
               viewState.showStudents(it)
           }
        }
        compositeDisposable.add(disposable)
    }

}