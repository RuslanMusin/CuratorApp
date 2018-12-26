package com.summer.itis.curatorapp.ui.skill.skill_list.edit

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.summer.itis.curatorapp.model.help.CuratorApi
import com.summer.itis.curatorapp.model.user.Curator
import com.summer.itis.curatorapp.repository.RepositoryProvider
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragPresenter
import com.summer.itis.curatorapp.utils.Const
import com.summer.itis.curatorapp.utils.Const.TAG_LOG
import io.reactivex.disposables.CompositeDisposable

@InjectViewState
class EditSkillsPresenter(): BaseFragPresenter<EditSkillsView>() {

    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun loadCuratorSkills(userId: String) {
        val disposable = RepositoryProvider.skillRepository.findCuratorSkills(userId).subscribe { res ->
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
                    it.body()?.let { skills ->
                        viewState.showSkills(skills)
                    }
                } else {
                    Log.d(Const.TAG_LOG, "failed subjects")

                }
            }
        }
        compositeDisposable.add(disposable)
    }

    fun updateCuratorSkills(curator: Curator) {
        curator.setSkillsIds()
        Log.d(TAG_LOG, "skill = ${curator.skillsIds[0]}")
        val disposable = RepositoryProvider.curatorRepository.update(curator.id, curator).subscribe { res ->
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
                    Log.d(Const.TAG_LOG, "successful updating curator skills")
                    it.body()?.let { skills ->
                        viewState.returnAfterEdit()
                    }
                } else {
                    Log.d(Const.TAG_LOG, "failed updating curator skills")

                }
            }
        }
        compositeDisposable.add(disposable)
    }

}