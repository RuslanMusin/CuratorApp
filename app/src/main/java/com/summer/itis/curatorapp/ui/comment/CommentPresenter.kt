package com.summer.itis.curatorapp.ui.comment

import android.util.Log
import com.summer.itis.curatorapp.model.comment.Comment
import com.summer.itis.curatorapp.repository.RepositoryProvider
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragPresenter
import com.summer.itis.curatorapp.utils.Const
import com.summer.itis.curatorapp.utils.Const.CURATOR_KEY
import com.summer.itis.curatorapp.utils.Const.STEP_KEY
import com.summer.itis.curatorapp.utils.Const.STEP_TYPE
import com.summer.itis.curatorapp.utils.Const.SUGGESTION_KEY
import com.summer.itis.curatorapp.utils.Const.SUGGESTION_TYPE
import com.summer.itis.curatorapp.utils.Const.WORK_KEY
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import retrofit2.adapter.rxjava2.Result

open class CommentPresenter<View: CommentView>: BaseFragPresenter<View>() {

    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun loadComments(map: HashMap<String, String>, type: String) {
        var single: Single<Result<List<Comment>>>? = null
        when(type) {
            STEP_TYPE -> {
                single = RepositoryProvider.commentRepository
                    .findStepComments(map.getValue(CURATOR_KEY), map.getValue(WORK_KEY), map.getValue(STEP_KEY))
            }

            SUGGESTION_TYPE -> {
                single = RepositoryProvider.commentRepository
                    .findSuggestionComments(map.getValue(CURATOR_KEY), map.getValue(SUGGESTION_KEY))
            }
        }
        val disposable = single?.let {
            it.doOnSubscribe({ viewState.showLoading(it) })
                .doAfterTerminate({ viewState.hideLoading() })
                .subscribe { res ->
                    Log.d(Const.TAG_LOG, "receive subjects response")
                    if (res == null) {
                        Log.d(Const.TAG_LOG, "res == null")
                    } else {
                        if (res.response() == null) {
                            Log.d(Const.TAG_LOG, "response == null and isError = ${res.isError}")
                            Log.d(Const.TAG_LOG, "error = ${res.error()?.message}")
                            res.error()?.printStackTrace()
                        }
                    }
                    res?.response()?.let {
                        if (it.isSuccessful) {
                            Log.d(Const.TAG_LOG, "successful subjects")
                            it.body()?.let { skills ->
                                viewState.showComments(skills)
                            }
                        } else {
                            Log.d(Const.TAG_LOG, "failed subjects")

                        }
                    }
                }
        }
        disposable?.let { compositeDisposable.add(it) }
    }

    fun createComment(map: HashMap<String, String>, type: String, comment: Comment) {
        var single: Single<Result<Comment>>? = null
        when(type) {
            STEP_TYPE -> {
                single = RepositoryProvider.commentRepository
                    .postStepComment(map.getValue(CURATOR_KEY), map.getValue(WORK_KEY), map.getValue(STEP_KEY), comment)
            }

            SUGGESTION_TYPE -> {
                single = RepositoryProvider.commentRepository
                    .postSuggestionComment(map.getValue(CURATOR_KEY), map.getValue(SUGGESTION_KEY), comment)
            }
        }
        val disposable = single
            ?.subscribe { res ->
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
//                        viewState.showComments(skills)
                    }
                } else {
                    Log.d(Const.TAG_LOG, "failed subjects")

                }
            }
        }
        disposable?.let { compositeDisposable.add(it) }
    }

    fun openCommentAuthor(userId: String) {
       /* userRepository.findById(userId).subscribe{ step ->
            viewState.goToCommentAuthor(step)
        }*/
    }

    /* fun loadComments(map: String) {
        val list: MutableList<Comment> = ArrayList()
        for(i in 1..10) {
            val comment = Comment()
            comment.authorId = "$i"
            comment.id = "$i"
            comment.authorName = AppHelper.currentCurator.name
            comment.authorPhotoUrl = STUB_PATH
            comment.createdDate = Date()
            comment.content = "Simple comment $i"
            list.add(comment)
        }
        viewState.hideLoading()
        viewState.showComments(list)
       *//* repository.getComments(map)
                .doOnSubscribe({ viewState.showLoading(it) })
                .doAfterTerminate({ viewState.hideLoading() })
                .subscribe{ comments ->
                    viewState?.showComments(comments)
                }*//*
    }*/

}