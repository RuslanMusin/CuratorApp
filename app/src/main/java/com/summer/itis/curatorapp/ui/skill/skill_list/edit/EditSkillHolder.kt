package com.summer.itis.curatorapp.ui.skill.skill_list.edit

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.summer.itis.curatorapp.R
import com.summer.itis.curatorapp.model.skill.Skill
import kotlinx.android.synthetic.main.layout_item_tv_with_clear.view.*


class EditSkillHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private lateinit var listener: EditSkillsView

    fun bind(item: Skill) {
        itemView.tv_added_skill_name.text = item.name
        itemView.iv_remove_skill.setOnClickListener { listener.remove(adapterPosition) }
    }


    companion object {

        fun create(parent: ViewGroup, listener: EditSkillsView): EditSkillHolder {
            val view =  LayoutInflater.from(parent.context).inflate(R.layout.item_skill_clear, parent, false);
            val holder = EditSkillHolder(view)
            holder.listener = listener
            return holder
        }
    }
}