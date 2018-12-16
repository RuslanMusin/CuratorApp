package com.summer.itis.curatorapp.ui.work.works

import com.summer.itis.curatorapp.model.work.Work
import com.summer.itis.curatorapp.ui.base.base_first.BaseRecyclerView
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragView

interface WorkListView: BaseFragView, BaseRecyclerView<Work> {

    fun showWorks(works: List<Work>)
}