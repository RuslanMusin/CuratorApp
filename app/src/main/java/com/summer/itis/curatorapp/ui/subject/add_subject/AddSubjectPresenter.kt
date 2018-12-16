package com.summer.itis.curatorapp.ui.subject.add_subject

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.summer.itis.curatorapp.model.skill.Subject
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
            Log.d(TAG_LOG, "receive subjects response")
            if(res == null) {
                Log.d(Const.TAG_LOG, "res == null")
            } else {
                if(res.response() == null) {
                    Log.d(Const.TAG_LOG, "response == null and isError = ${res.isError}")
                    Log.d(TAG_LOG, "error = ${res.error()?.message}")
                    res.error()?.printStackTrace()
                }
            }
            res?.response()?.let {
                if (it.isSuccessful) {
                    Log.d(Const.TAG_LOG, "successful subjects")
                    it.body()?.let { suggestions ->
                        viewState.showSubjects(suggestions)
                    }
                } else {
                    Log.d(Const.TAG_LOG, "failed subjects")

                }
            }
        }
        compositeDisposable.add(disposable)
    }

   /* fun loadSubjects() : List<Subject>{
        val subjects: MutableList<Subject> = ArrayList()


        for(i in 1..10) {
            val subject = Subject()
            subject.id = "$i"
            if(i / 2 == 0) {
                subject.name = "Android $i"
            } else {
                subject.name = "Java EE $i"
            }
            subjects.add(subject)
        }
        return subjects
        *//*val disposable = studentRepository
                .findAll()
                .doOnSubscribe(Consumer<Disposable> { viewState.showLoading(it) })
                .doAfterTerminate(Action { viewState.hideLoading() })
                .subscribe ({ viewState.changeDataSet(it) }, { viewState.handleError(it) })
        compositeDisposable.add(disposable)*//*
    }*/

}