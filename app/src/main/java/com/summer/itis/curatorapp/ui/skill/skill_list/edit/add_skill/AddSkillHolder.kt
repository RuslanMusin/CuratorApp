package com.summer.itis.curatorapp.ui.skill.skill_list.edit.add_skill

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.summer.itis.curatorapp.R
import com.summer.itis.curatorapp.model.skill.Skill
import kotlinx.android.synthetic.main.item_skill.view.*

class AddSkillHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(item: Skill) {
        itemView.tv_skill.text = item.name

    }


    companion object {

        fun create(parent: ViewGroup): AddSkillHolder {
            val view =  LayoutInflater.from(parent.context).inflate(R.layout.item_skill, parent, false);
            val holder = AddSkillHolder(view)
            return holder
        }
    }
}