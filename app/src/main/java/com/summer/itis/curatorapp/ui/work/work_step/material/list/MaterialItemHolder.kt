package com.summer.itis.curatorapp.ui.work.work_step.material.list

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.summer.itis.curatorapp.R
import com.summer.itis.curatorapp.model.material.Material
import com.summer.itis.curatorapp.model.skill.Skill
import kotlinx.android.synthetic.main.item_material.view.*

class MaterialItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(item: Material) {
        itemView.tv_material.text = item.content
    }


    companion object {

        fun create(parent: ViewGroup): MaterialItemHolder {
            val view =  LayoutInflater.from(parent.context).inflate(R.layout.item_material, parent, false);
            val holder = MaterialItemHolder(view)
            return holder
        }
    }
}
