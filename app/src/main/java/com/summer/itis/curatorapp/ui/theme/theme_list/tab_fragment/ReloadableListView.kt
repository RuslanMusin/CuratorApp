package com.summer.itis.curatorapp.ui.theme.theme_list.tab_fragment

import com.summer.itis.curatorapp.ui.base.base_custom.SearchListener

interface ReloadableListView: SearchListener {

    fun reloadList()
}