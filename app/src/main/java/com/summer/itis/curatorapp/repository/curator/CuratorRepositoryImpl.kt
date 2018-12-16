package com.summer.itis.curatorapp.repository.curator

import com.summer.itis.curatorapp.api.services.CuratorService
import com.summer.itis.curatorapp.model.comment.Comment
import com.summer.itis.curatorapp.model.skill.Skill
import com.summer.itis.curatorapp.model.step.Step
import com.summer.itis.curatorapp.model.theme.SuggestionTheme
import com.summer.itis.curatorapp.model.theme.Theme
import com.summer.itis.curatorapp.model.user.Curator
import com.summer.itis.curatorapp.model.work.Work
import com.summer.itis.curatorapp.utils.RxUtils
import io.reactivex.Single
import retrofit2.adapter.rxjava2.Result

class CuratorRepositoryImpl(val apiService: CuratorService) :
        CuratorRepository {

    override lateinit var currentCurator: Curator

    override fun findById(id: String): Single<Result<Curator>> {
        return apiService.findById(id).compose(RxUtils.asyncSingle())
    }

    override fun findAll(): Single<Result<List<Curator>>> {
        return apiService.findAll().compose(RxUtils.asyncSingle())
    }

    override fun findSkills(id: String): Single<Result<List<Skill>>> {
        return apiService.findSkills(id).compose(RxUtils.asyncSingle())
    }

    override fun findSuggestions(id: String): Single<Result<List<SuggestionTheme>>> {
        return apiService.findSuggestions(id).compose(RxUtils.asyncSingle())
    }

    override fun findSuggestion(curatorId: String, suggestionsId: String): Single<Result<SuggestionTheme>> {
        return apiService.findSuggestion(curatorId, suggestionsId).compose(RxUtils.asyncSingle())
    }

    override fun findSuggestionComments(curatorId: String, suggestionsId: String): Single<Result<List<Comment>>> {
        return apiService.findSuggestionComments(curatorId, suggestionsId).compose(RxUtils.asyncSingle())
    }

    override fun findThemes(id: String): Single<Result<List<Theme>>> {
        return apiService.findThemes(id).compose(RxUtils.asyncSingle())
    }

    override fun findTheme(curatorId: String, themeId: String): Single<Result<Theme>> {
        return apiService.findTheme(curatorId, themeId).compose(RxUtils.asyncSingle())
    }

    override fun findWorks(curatorId: String): Single<Result<List<Work>>> {
        return apiService.findWorks(curatorId).compose(RxUtils.asyncSingle())
    }

    override fun findWork(curatorId: String, workId: String): Single<Result<Work>> {
        return apiService.findWork(curatorId, workId).compose(RxUtils.asyncSingle())
    }

    override fun findWorkSteps(curatorId: String, workId: String): Single<Result<List<Step>>> {
        return apiService.findWorkSteps(curatorId, workId).compose(RxUtils.asyncSingle())
    }

    override fun findWorkStep(curatorId: String, workId: String, stepId: String): Single<Result<Step>> {
        return apiService.findWorkStep(curatorId, workId, stepId).compose(RxUtils.asyncSingle())
    }

    override fun findStepComments(curatorId: String, workId: String, stepId: String): Single<Result<List<Comment>>> {
        return apiService.findStepComments(curatorId, workId, stepId).compose(RxUtils.asyncSingle())
    }

    override fun findStepMaterials(curatorId: String, workId: String, stepId: String): Single<Result<List<String>>> {
        return apiService.findStepMaterials(curatorId, workId, stepId).compose(RxUtils.asyncSingle())
    }

    override fun update(id: String, curator: Curator): Single<Result<Curator>> {
        return apiService.update(id, curator).compose(RxUtils.asyncSingle())
    }

    override fun postSuggestion(id: String, suggestion: SuggestionTheme): Single<Result<SuggestionTheme>> {
        return apiService.postSuggestion(id, suggestion).compose(RxUtils.asyncSingle())
    }

    override fun updateSuggestion(curatorId: String, suggestion: SuggestionTheme): Single<Result<SuggestionTheme>> {
        return apiService.updateSuggestion(curatorId, suggestion.id, suggestion).compose(RxUtils.asyncSingle())
    }

    override fun deleteSuggestion(curatorId: String, suggestionsId: String): Single<Result<SuggestionTheme>> {
        return apiService.deleteSuggestion(curatorId, suggestionsId).compose(RxUtils.asyncSingle())
    }

    override fun postTheme(id: String, theme: Theme): Single<Result<Theme>> {
        return apiService.postTheme(id, theme).compose(RxUtils.asyncSingle())
    }

    override fun updateTheme(curatorId: String, theme: Theme): Single<Result<Theme>> {
        return apiService.updateTheme(curatorId, theme.id, theme).compose(RxUtils.asyncSingle())
    }

    override fun deleteTheme(curatorId: String, themeId: String): Single<Result<Theme>> {
        return apiService.deleteTheme(curatorId, themeId).compose(RxUtils.asyncSingle())
    }

    override fun postWork(id: String, work: Work): Single<Result<Work>> {
        return apiService.postWork(id, work).compose(RxUtils.asyncSingle())
    }

    override fun updateWork(curatorId: String, work: Work): Single<Result<Work>> {
        return apiService.updateWork(curatorId, work.id, work).compose(RxUtils.asyncSingle())
    }

    override fun deleteWork(curatorId: String, workId: String): Single<Result<Work>> {
        return apiService.deleteWork(curatorId, workId).compose(RxUtils.asyncSingle())
    }

    override fun postWorkStep(curatorId: String, workId: String, step: Step): Single<Result<Step>> {
        return apiService.postWorkStep(curatorId, workId, step).compose(RxUtils.asyncSingle())
    }

    override fun updateWorkStep(curatorId: String, workId: String, step: Step): Single<Result<Step>> {
        return apiService.updateWorkStep(curatorId, workId, step.id, step).compose(RxUtils.asyncSingle())
    }

    override fun deleteWorkStep(curatorId: String, workId: String, stepId: String): Single<Result<Step>> {
        return apiService.deleteWorkStep(curatorId, workId, stepId).compose(RxUtils.asyncSingle())
    }

    override fun postWorkStepComment(curatorId: String, workId: String, stepId: String, comment: Comment): Single<Result<Comment>> {
        return apiService.postWorkStepComment(curatorId, workId, stepId, comment).compose(RxUtils.asyncSingle())
    }

    override fun postWorkStepMaterial(curatorId: String, workId: String, stepId: String, link: String): Single<Result<String>> {
        return apiService.postWorkStepMaterial(curatorId, workId, stepId, link).compose(RxUtils.asyncSingle())
    }
}