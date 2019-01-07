package com.summer.itis.curatorapp.ui.work.work_step.material.list

import com.summer.itis.curatorapp.model.material.Material
import com.summer.itis.curatorapp.ui.base.base_first.BaseRecyclerView
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragView

interface MaterialListView: BaseFragView, BaseRecyclerView<Material> {

    fun showSkills(skills: List<Material>)
}