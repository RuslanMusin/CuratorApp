package com.summer.itis.curatorapp.ui.comment

import com.summer.itis.curatorapp.R
import com.summer.itis.curatorapp.model.comment.Comment
import com.summer.itis.curatorapp.repository.RepositoryProvider
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragPresenter
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
                   interceptSecondResponse(res, {
                       viewState.showComments(it)
                   },
                       R.string.failed_load_comments)
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
                interceptSecondResponse(res, {}, R.string.failed_post_comment)
            }
        disposable?.let { compositeDisposable.add(it) }
    }

    fun openCommentAuthor(userId: String) {

    }

}