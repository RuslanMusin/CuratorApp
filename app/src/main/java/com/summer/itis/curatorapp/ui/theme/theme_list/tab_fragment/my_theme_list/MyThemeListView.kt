package com.summer.itis.curatorapp.ui.theme.theme_list.tab_fragment.my_theme_list

import com.summer.itis.curatorapp.model.theme.Theme
import com.summer.itis.curatorapp.model.user.Student
import com.summer.itis.curatorapp.ui.base.base_custom.SearchListener
import com.summer.itis.curatorapp.ui.base.base_first.BaseRecyclerView
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragView

interface MyThemeListView: BaseFragView, BaseRecyclerView<Theme>, SearchListener {

    fun openStudentAction(adapterPosition: Int)

    fun showThemes(themes: List<Theme>)

    fun updateFakeStudents(students: List<Student>)
}