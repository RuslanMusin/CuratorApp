package com.summer.itis.curatorapp.repository.curator

import com.summer.itis.curatorapp.model.comment.Comment
import com.summer.itis.curatorapp.model.skill.Skill
import com.summer.itis.curatorapp.model.step.Step
import com.summer.itis.curatorapp.model.theme.SuggestionTheme
import com.summer.itis.curatorapp.model.theme.Theme
import com.summer.itis.curatorapp.model.user.Curator
import com.summer.itis.curatorapp.model.work.Work
import com.summer.itis.curatorapp.repository.base.BaseRepository
import io.reactivex.Single
import retrofit2.adapter.rxjava2.Result

interface CuratorRepository {

    var currentCurator: Curator

    //CRUD
    fun findById(id: String): Single<Result<Curator>>

    fun findAll(): Single<Result<List<Curator>>>
    fun findSkills(id: String): Single<Result<List<Skill>>>
    fun findSuggestions(id: String): Single<Result<List<SuggestionTheme>>>
    fun findSuggestion(curatorId: String, suggestionsId: String): Single<Result<SuggestionTheme>>
    fun findSuggestionComments(curatorId: String, suggestionsId: String): Single<Result<List<Comment>>>
    fun findThemes(id: String): Single<Result<List<Theme>>>
    fun findWorks(id: String): Single<Result<List<Work>>>
    fun findTheme(curatorId: String, themeId: String): Single<Result<Theme>>
    fun findWork(curatorId: String, workId: String): Single<Result<Work>>
    fun findWorkSteps(curatorId: String, workId: String): Single<Result<List<Step>>>
    fun findWorkStep(curatorId: String, workId: String, stepId: String): Single<Result<Step>>
    fun findStepComments(curatorId: String, workId: String, stepId: String): Single<Result<List<Comment>>>
    fun findStepMaterials(curatorId: String, workId: String, stepId: String): Single<Result<List<String>>>
    fun update(id: String, curator: Curator): Single<Result<Curator>>
    fun postSuggestion(id: String, suggestion: SuggestionTheme): Single<Result<SuggestionTheme>>
    fun updateSuggestion(curatorId: String, suggestion: SuggestionTheme): Single<Result<SuggestionTheme>>
    fun deleteSuggestion(curatorId: String, suggestionsId: String): Single<Result<SuggestionTheme>>
    fun postTheme(id: String, theme: Theme): Single<Result<Theme>>
    fun updateTheme(curatorId: String, theme: Theme): Single<Result<Theme>>
    fun deleteTheme(curatorId: String, themeId: String): Single<Result<Theme>>
    fun postWork(id: String, work: Work): Single<Result<Work>>
    fun updateWork(curatorId: String, work: Work): Single<Result<Work>>
    fun deleteWork(curatorId: String, workId: String): Single<Result<Work>>
    fun postWorkStep(curatorId: String, workId: String, step: Step): Single<Result<Step>>
    fun updateWorkStep(curatorId: String, workId: String, step: Step): Single<Result<Step>>
    fun deleteWorkStep(curatorId: String, workId: String, stepId: String): Single<Result<Step>>
    fun postWorkStepComment(
        curatorId: String,
        workId: String,
        stepId: String,
        comment: Comment
    ): Single<Result<Comment>>

    fun postWorkStepMaterial(curatorId: String, workId: String, stepId: String, link: String): Single<Result<String>>
}