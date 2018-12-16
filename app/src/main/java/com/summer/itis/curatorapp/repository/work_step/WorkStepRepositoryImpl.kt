package com.summer.itis.curatorapp.repository.work_step

import com.summer.itis.curatorapp.api.services.WorkStepService
import com.summer.itis.curatorapp.model.comment.Comment
import com.summer.itis.curatorapp.model.help.StepApi
import com.summer.itis.curatorapp.model.step.Step
import com.summer.itis.curatorapp.utils.RxUtils
import io.reactivex.Single
import retrofit2.adapter.rxjava2.Result

class WorkStepRepositoryImpl(val apiService: WorkStepService): WorkStepRepository {

    override fun findAll(workId: String): Single<Result<List<Step>>> {
        return apiService.findAll(workId).compose(RxUtils.asyncSingle())
    }

    override fun findById(workId: String, stepId: String): Single<Result<Step>> {
        return apiService.findById(workId, stepId).compose(RxUtils.asyncSingle())
    }

    override fun findCuratorWorkSteps(curatorId: String, workId: String): Single<Result<List<Step>>> {
        return apiService.findCuratorWorkSteps(curatorId, workId).compose(RxUtils.asyncSingle())
    }

    override fun findCuratorWorkStep(curatorId: String, workId: String, stepId: String): Single<Result<Step>> {
        return apiService.findCuratorWorkStep(curatorId, workId, stepId).compose(RxUtils.asyncSingle())
    }

    override fun findCuratorStepComments(curatorId: String, workId: String, stepId: String): Single<Result<List<Comment>>> {
        return apiService.findCuratorStepComments(curatorId, workId, stepId).compose(RxUtils.asyncSingle())
    }

    override fun findCuratorStepMaterials(curatorId: String, workId: String, stepId: String): Single<Result<List<String>>> {
        return apiService.findCuratorStepMaterials(curatorId, workId, stepId).compose(RxUtils.asyncSingle())
    }

    override fun postCuratorWorkStep(curatorId: String, workId: String, step: StepApi): Single<Result<Step>> {
        return apiService.postCuratorWorkStep(curatorId, workId, step).compose(RxUtils.asyncSingle())
    }

    override fun updateCuratorWorkStep(curatorId: String, workId: String, step: StepApi): Single<Result<Step>> {
        return apiService.updateCuratorWorkStep(curatorId, workId, step.id, step).compose(RxUtils.asyncSingle())
    }

    override fun deleteCuratorWorkStep(curatorId: String, workId: String, stepId: String): Single<Result<Step>> {
        return apiService.deleteCuratorWorkStep(curatorId, workId, stepId).compose(RxUtils.asyncSingle())
    }

    override fun postCuratorWorkStepComment(curatorId: String, workId: String, stepId: String, comment: Comment): Single<Result<Comment>> {
        return apiService.postCuratorWorkStepComment(curatorId, workId, stepId, comment).compose(RxUtils.asyncSingle())
    }

    override fun postCuratorWorkStepMaterial(curatorId: String, workId: String, stepId: String, link: String): Single<Result<String>> {
        return apiService.postCuratorWorkStepMaterial(curatorId, workId, stepId, link).compose(RxUtils.asyncSingle())
    }

}