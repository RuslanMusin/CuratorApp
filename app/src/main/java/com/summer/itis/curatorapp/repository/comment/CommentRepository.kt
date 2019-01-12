package com.summer.itis.curatorapp.repository.comment

import com.summer.itis.curatorapp.model.comment.Comment
import io.reactivex.Single
import retrofit2.adapter.rxjava2.Result

interface CommentRepository {

    fun findStepComments(curatorId: String, workId: String, stepId: String): Single<Result<List<Comment>>>

    fun postStepComment(
        curatorId: String,
        workId: String,
        stepId: String,
        comment: Comment
    ): Single<Result<Comment>>

    fun findSuggestionComments(curatorId: String, suggestionId: String): Single<Result<List<Comment>>>

    fun postSuggestionComment(
        curatorId: String,
        suggestionId: String,
        comment: Comment
    ): Single<Result<Comment>>

}