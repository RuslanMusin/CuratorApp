package com.summer.itis.curatorapp.ui.skill.skill_list.view

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.summer.itis.curatorapp.repository.RepositoryProvider
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragPresenter
import com.summer.itis.curatorapp.utils.Const
import io.reactivex.disposables.CompositeDisposable

@InjectViewState
class SkillListPresenter(): BaseFragPresenter<SkillListView>() {

    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun loadCuratorSkills(userId: String) {
        val disposable = RepositoryProvider.skillRepository.findCuratorSkills(userId).subscribe { res ->
            interceptResponse(res) {
                viewState.showSkills(it)
            }
        }
        compositeDisposable.add(disposable)
    }

    fun loadStudentSkills(userId: String) {
        val disposable = RepositoryProvider.skillRepository.findStudentSkills(userId).subscribe { res ->
            interceptResponse(res) {
                viewState.showSkills(it)
            }
        }
        compositeDisposable.add(disposable)
    }

}