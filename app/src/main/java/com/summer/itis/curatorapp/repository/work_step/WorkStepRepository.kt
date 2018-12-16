package com.summer.itis.curatorapp.repository.work_step

import com.summer.itis.curatorapp.model.comment.Comment
import com.summer.itis.curatorapp.model.help.StepApi
import com.summer.itis.curatorapp.model.step.Step
import io.reactivex.Single
import retrofit2.adapter.rxjava2.Result

interface WorkStepRepository {

    fun findAll(workId: String): Single<Result<List<Step>>>

    fun findById(workId: String, stepId: String): Single<Result<Step>>

    fun findCuratorWorkSteps(curatorId: String, workId: String): Single<Result<List<Step>>>
    fun findCuratorWorkStep(curatorId: String, workId: String, stepId: String): Single<Result<Step>>
    fun findCuratorStepComments(curatorId: String, workId: String, stepId: String): Single<Result<List<Comment>>>
    fun findCuratorStepMaterials(curatorId: String, workId: String, stepId: String): Single<Result<List<String>>>

    fun postCuratorWorkStep(curatorId: String, workId: String, step: StepApi): Single<Result<Step>>
    fun updateCuratorWorkStep(curatorId: String, workId: String, step: StepApi): Single<Result<Step>>
    fun deleteCuratorWorkStep(curatorId: String, workId: String, stepId: String): Single<Result<Step>>

    fun postCuratorWorkStepComment(
        curatorId: String,
        workId: String,
        stepId: String,
        comment: Comment
    ): Single<Result<Comment>>

    fun postCuratorWorkStepMaterial(curatorId: String, workId: String, stepId: String, link: String): Single<Result<String>>

}