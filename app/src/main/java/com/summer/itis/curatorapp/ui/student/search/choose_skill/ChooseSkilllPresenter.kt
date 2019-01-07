package com.summer.itis.curatorapp.ui.student.search.choose_skill

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.summer.itis.curatorapp.repository.RepositoryProvider
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragPresenter
import com.summer.itis.curatorapp.utils.Const
import io.reactivex.disposables.CompositeDisposable

@InjectViewState
class ChooseSkilllPresenter(): BaseFragPresenter<ChooseSkillView>() {

    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun loadSkills() {
        val disposable = RepositoryProvider.skillRepository.findAll().subscribe { res ->
            interceptResponse(res) {
                viewState.showSkills(it)

            }
        }
        compositeDisposable.add(disposable)
    }


}