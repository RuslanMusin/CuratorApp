package com.summer.itis.curatorapp.repository

import com.summer.itis.curatorapp.api.ApiFactory.Companion.curatorService
import com.summer.itis.curatorapp.api.ApiFactory.Companion.skillService
import com.summer.itis.curatorapp.api.ApiFactory.Companion.studentService
import com.summer.itis.curatorapp.api.ApiFactory.Companion.subjectService
import com.summer.itis.curatorapp.api.ApiFactory.Companion.suggestionService
import com.summer.itis.curatorapp.api.ApiFactory.Companion.themeService
import com.summer.itis.curatorapp.api.ApiFactory.Companion.workService
import com.summer.itis.curatorapp.api.ApiFactory.Companion.workStepService
import com.summer.itis.curatorapp.repository.auth.AuthRepository
import com.summer.itis.curatorapp.repository.auth.AuthRepositoryImpl
import com.summer.itis.curatorapp.repository.curator.CuratorRepository
import com.summer.itis.curatorapp.repository.curator.CuratorRepositoryImpl
import com.summer.itis.curatorapp.repository.skill.SkillRepository
import com.summer.itis.curatorapp.repository.skill.SkillRepositoryImpl
import com.summer.itis.curatorapp.repository.student.StudentRepositoryImpl
import com.summer.itis.curatorapp.repository.subject.SubjectRepository
import com.summer.itis.curatorapp.repository.subject.SubjectRepositoryImpl
import com.summer.itis.curatorapp.repository.suggestion.SuggestionRepository
import com.summer.itis.curatorapp.repository.suggestion.SuggestionRepositoryImpl
import com.summer.itis.curatorapp.repository.theme.ThemeRepository
import com.summer.itis.curatorapp.repository.theme.ThemeRepositoryImpl
import com.summer.itis.curatorapp.repository.work.WorkRepository
import com.summer.itis.curatorapp.repository.work.WorkRepositoryImpl
import com.summer.itis.curatorapp.repository.work_step.WorkStepRepository
import com.summer.itis.curatorapp.repository.work_step.WorkStepRepositoryImpl


class RepositoryProvider {

    companion object {

        //table_names
        const val USERS = "users"
        const val USER_FRIENDS = "user_friends"
        const val USERS_ABSTRACT_CARDS = "users_abstract_cards"
        const val USERS_TESTS = "users_tests"
        const val USERS_CARDS = "users_cards"
        const val ABSTRACT_CARDS = "abstract_cards"
        const val CARDS = "test_cards"
        const val TESTS = "tests"
        const val TEST_COMMENTS = "test_comments"
        const val CARD_COMMENTS = "card_comments"
        const val LOBBIES = "lobbies"
        const val USERS_LOBBIES = "users_lobbies"

        val authRepository: AuthRepository by lazy {
            AuthRepositoryImpl()
        }

        val curatorRepository: CuratorRepository by lazy {
            CuratorRepositoryImpl(curatorService)
        }

        val studentRepository: StudentRepositoryImpl by lazy {
            StudentRepositoryImpl(studentService)
        }

        val skillRepository: SkillRepository by lazy {
            SkillRepositoryImpl(skillService)
        }

        val subjectRepository: SubjectRepository by lazy {
            SubjectRepositoryImpl(subjectService)
        }

        val themeRepository: ThemeRepository by lazy {
            ThemeRepositoryImpl(themeService)
        }

        val suggestionRepository: SuggestionRepository by lazy {
            SuggestionRepositoryImpl(suggestionService)
        }

        val worksRepository: WorkRepository by lazy {
            WorkRepositoryImpl(workService)
        }

        val workStepRepository: WorkStepRepository by lazy {
            WorkStepRepositoryImpl(workStepService)
        }

    }
}