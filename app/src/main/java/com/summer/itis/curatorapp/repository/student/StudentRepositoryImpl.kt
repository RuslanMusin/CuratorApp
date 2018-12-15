package com.summer.itis.curatorapp.repository.student

import com.summer.itis.curatorapp.api.services.StudentService
import com.summer.itis.curatorapp.model.user.Student
import com.summer.itis.curatorapp.repository.base.BaseRepositoryImpl

class StudentRepositoryImpl(override val apiService: StudentService) :
        StudentRepository, BaseRepositoryImpl<StudentService, Student>() {

}