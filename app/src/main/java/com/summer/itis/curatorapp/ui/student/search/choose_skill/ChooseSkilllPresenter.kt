package com.summer.itis.curatorapp.ui.student.search.choose_skill

import com.arellomobile.mvp.InjectViewState
import com.summer.itis.curatorapp.repository.RepositoryProvider
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragPresenter
import io.reactivex.disposables.CompositeDisposable

@InjectViewState
class ChooseSkilllPresenter(): BaseFragPresenter<ChooseSkillView>() {

    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun loadSkills() {
        viewState.startTimeout { loadSkills() }
        val disposable = RepositoryProvider.skillRepository.findAll().subscribe { res ->
            interceptSecondResponse(res, {
                viewState.stopTimeout()
                viewState.showSkills(it)

            },{ loadSkills() })
        }
        compositeDisposable.add(disposable)
    }


}