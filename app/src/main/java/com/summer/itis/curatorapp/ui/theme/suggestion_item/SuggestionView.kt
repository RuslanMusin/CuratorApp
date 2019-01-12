package com.summer.itis.curatorapp.ui.theme.suggestion_item

import com.summer.itis.curatorapp.model.theme.Status
import com.summer.itis.curatorapp.ui.comment.CommentView

interface SuggestionView: CommentView {

    fun setStatus(status: String)

    fun hideEdit(status: Status)

}