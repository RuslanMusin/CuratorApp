package com.summer.itis.curatorapp.ui.skill.skill_list.edit

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.summer.itis.curatorapp.R
import com.summer.itis.curatorapp.model.user.Curator
import com.summer.itis.curatorapp.repository.RepositoryProvider
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragPresenter
import com.summer.itis.curatorapp.utils.Const.TAG_LOG
import io.reactivex.disposables.CompositeDisposable

@InjectViewState
class EditSkillsPresenter(): BaseFragPresenter<EditSkillsView>() {

    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun loadCuratorSkills(userId: String) {
        val disposable = RepositoryProvider.skillRepository.findCuratorSkills(userId).subscribe { res ->
            interceptSecondResponse(res, {
                viewState.showSkills(it)
            },{ loadCuratorSkills(userId)})
        }
        compositeDisposable.add(disposable)
    }

    fun updateCuratorSkills(curator: Curator) {
        curator.setSkillsIds()
        Log.d(TAG_LOG, "skill = ${curator.skillsIds[0]}")
        val disposable = RepositoryProvider.curatorRepository.update(curator.id, curator).subscribe { res ->
           interceptSecondResponse(res, {
               viewState.returnAfterEdit()
           },
               R.string.failed_update_skills)
        }
        compositeDisposable.add(disposable)
    }

}