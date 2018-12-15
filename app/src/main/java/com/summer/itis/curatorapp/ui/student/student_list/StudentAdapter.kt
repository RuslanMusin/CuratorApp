package com.summer.itis.curatorapp.ui.student.student_list

import android.view.ViewGroup
import com.summer.itis.curatorapp.model.user.Student
import com.summer.itis.curatorapp.ui.base.base_first.BaseAdapter

class StudentAdapter(items: MutableList<Student>) : BaseAdapter<Student, StudentItemHolder>(items) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentItemHolder {
        return StudentItemHolder.create(parent)
    }

    override fun onBindViewHolder(holder: StudentItemHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val item = getItem(position)
        holder.bind(item)
    }
}