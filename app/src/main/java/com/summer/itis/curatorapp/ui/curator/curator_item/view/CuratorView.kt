package com.summer.itis.curatorapp.ui.curator.curator_item.view

import com.summer.itis.curatorapp.model.user.Curator
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragView

interface CuratorView: BaseFragView {

    fun initViews(curator: Curator, type: String)

    fun logout()

}