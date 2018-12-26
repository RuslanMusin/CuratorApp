package com.summer.itis.curatorapp.ui.student.student_item

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.summer.itis.curatorapp.model.user.Student
import com.summer.itis.curatorapp.repository.RepositoryProvider
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragPresenter
import com.summer.itis.curatorapp.utils.Const
import io.reactivex.disposables.CompositeDisposable

@InjectViewState
class StudentPresenter(): BaseFragPresenter<StudentView>() {

    val compositeDisposable: CompositeDisposable = CompositeDisposable()


    /* fun findStudentById(id: String) {
         val student = Student()
         student.id = id
         student.name = "Ruslan"
         student.lastname = "Musin"
         student.patronymic = "Martovich"
         student.description = "usual desc"
         student.groupNumber = "11-603"
         student.courseNumber = 3
         viewState.setUserData(student)
     }*/

    fun loadStudent(studentId: String) {
        val disposable = RepositoryProvider.studentRepository.findById(studentId).subscribe { res ->
            Log.d(Const.TAG_LOG, "receive subjects response")
            if(res == null) {
                Log.d(Const.TAG_LOG, "res == null")
            } else {
                if(res.response() == null) {
                    Log.d(Const.TAG_LOG, "response == null and isError = ${res.isError}")
                    Log.d(Const.TAG_LOG, "error = ${res.error()?.message}")
                    res.error()?.printStackTrace()
                }
            }
            res?.response()?.let {
                if (it.isSuccessful) {
                    Log.d(Const.TAG_LOG, "successful subjects")
                    it.body()?.let { students ->
                        viewState.showStudent(students)
                    }
                } else {
                    Log.d(Const.TAG_LOG, "failed subjects")

                }
            }
        }
        compositeDisposable.add(disposable)
    }

}