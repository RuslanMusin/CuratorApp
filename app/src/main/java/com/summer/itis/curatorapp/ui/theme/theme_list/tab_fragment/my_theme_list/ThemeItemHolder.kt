package com.summer.itis.curatorapp.ui.theme.theme_list.tab_fragment.my_theme_list

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.summer.itis.curatorapp.R
import com.summer.itis.curatorapp.model.skill.Subject
import com.summer.itis.curatorapp.model.theme.Theme
import kotlinx.android.synthetic.main.item_theme.view.*
import java.util.*

class ThemeItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private lateinit var listener: MyThemeListView

    fun bind(item: Theme) {
        itemView.tv_theme.text = item.title
//        itemView.tv_subject.content = item.subject.name
        itemView.setOnLongClickListener {
            listener.openStudentAction(adapterPosition)
            true
        }
    }


    companion object {

        fun create(parent: ViewGroup, listener: MyThemeListView): ThemeItemHolder {
            val view =  LayoutInflater.from(parent.context).inflate(R.layout.item_theme, parent, false);
            val holder = ThemeItemHolder(view)
            holder.listener = listener
            return holder
        }
    }
}
