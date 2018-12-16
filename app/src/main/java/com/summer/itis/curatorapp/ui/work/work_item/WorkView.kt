package com.summer.itis.curatorapp.ui.work.work_item

import com.summer.itis.curatorapp.model.work.Work
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragView

interface WorkView: BaseFragView {

    fun showWork(work: Work)

}