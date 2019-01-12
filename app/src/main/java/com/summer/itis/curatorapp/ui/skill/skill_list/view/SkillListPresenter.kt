package com.summer.itis.curatorapp.ui.skill.skill_list.view

import com.arellomobile.mvp.InjectViewState
import com.summer.itis.curatorapp.repository.RepositoryProvider
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragPresenter
import io.reactivex.disposables.CompositeDisposable

@InjectViewState
class SkillListPresenter(): BaseFragPresenter<SkillListView>() {

    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun loadCuratorSkills(userId: String) {
        viewState.startTimeout { loadCuratorSkills(userId) }
        val disposable = RepositoryProvider.skillRepository.findCuratorSkills(userId).subscribe { res ->
            interceptSecondResponse(res, {
                viewState.stopTimeout()
                viewState.showSkills(it)
            },{loadCuratorSkills(userId)})
        }
        compositeDisposable.add(disposable)
    }

    fun loadStudentSkills(userId: String) {
        viewState.startTimeout { loadStudentSkills(userId) }
        val disposable = RepositoryProvider.skillRepository.findStudentSkills(userId).subscribe { res ->
            interceptSecondResponse(res, {
                viewState.stopTimeout()
                viewState.showSkills(it)
            },{loadStudentSkills(userId)})
        }
        compositeDisposable.add(disposable)
    }

}