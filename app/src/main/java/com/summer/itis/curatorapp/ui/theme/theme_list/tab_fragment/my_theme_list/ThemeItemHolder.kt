package com.summer.itis.curatorapp.ui.theme.theme_list.tab_fragment.my_theme_list

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.summer.itis.curatorapp.R
import com.summer.itis.curatorapp.model.theme.Theme
import kotlinx.android.synthetic.main.item_theme.view.*

class ThemeItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private lateinit var listener: MyThemeListView

    fun bind(item: Theme) {
        itemView.tv_theme.text = item.title
        itemView.tv_student.text = getStudentName(itemView, item)
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

    private fun getStudentName(view: View, theme: Theme): String? {
        var name = theme.student?.name
        if(name == null) {
            name = view.context.getString(R.string.theme_not_choosed)
        }
        return name
    }
}
