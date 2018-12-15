package com.summer.itis.curatorapp.ui.theme.add_theme

import android.content.Intent
import com.summer.itis.curatorapp.ui.base.base_first.fragment.BaseFragView

interface AddThemeView: BaseFragView {

    fun getResultAfterEdit(isEdit: Boolean, intent: Intent?)
}