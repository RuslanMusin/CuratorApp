package com.summer.itis.curatorapp.ui.theme.theme_list.tab_fragment.suggestion_list

import com.summer.itis.curatorapp.model.theme.Suggestion
import com.summer.itis.curatorapp.ui.base.base_custom.SearchListener
import com.summer.itis.curatorapp.ui.base.base_first.BaseRecyclerView
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragView
import com.summer.itis.curatorapp.ui.theme.theme_list.tab_fragment.ReloadableListView

interface SuggestionListView: BaseFragView, BaseRecyclerView<Suggestion>, ReloadableListView {

    fun chooseUserFakeAction(pos: Int)

    fun showSuggestions(suggestions: List<Suggestion>)
}