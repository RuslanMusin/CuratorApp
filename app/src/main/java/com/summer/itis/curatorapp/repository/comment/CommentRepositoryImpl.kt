package com.summer.itis.curatorapp.repository.comment

import com.summer.itis.curatorapp.api.services.CommentService
import com.summer.itis.curatorapp.model.comment.Comment
import com.summer.itis.curatorapp.utils.RxUtils
import io.reactivex.Single
import retrofit2.adapter.rxjava2.Result

class CommentRepositoryImpl(val apiService: CommentService): CommentRepository {

    override fun findStepComments(curatorId: String, workId: String, stepId: String): Single<Result<List<Comment>>> {
        return apiService.findStepComments(curatorId, workId, stepId).compose(RxUtils.asyncSingle())
    }

    override fun postStepComment(
        curatorId: String,
        workId: String,
        stepId: String,
        comment: Comment
    ): Single<Result<Comment>> {
        return apiService.postStepComment(curatorId, workId, stepId, comment).compose(RxUtils.asyncSingle())
    }

    override fun findSuggestionComments(curatorId: String, suggestionId: String): Single<Result<List<Comment>>> {
        return apiService.findSuggestionComments(curatorId, suggestionId).compose(RxUtils.asyncSingle())
    }

    override fun postSuggestionComment(
        curatorId: String,
        suggestionId: String,
        comment: Comment
    ): Single<Result<Comment>> {
        return apiService.postSuggestionComment(curatorId, suggestionId, comment).compose(RxUtils.asyncSingle())
    }

}