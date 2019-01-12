package com.summer.itis.curatorapp.ui.work.work_step.material.list

import android.view.ViewGroup
import com.summer.itis.curatorapp.model.material.Material
import com.summer.itis.curatorapp.ui.base.base_first.BaseAdapter

class MaterialAdapter(items: MutableList<Material>) : BaseAdapter<Material, MaterialItemHolder>(items) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MaterialItemHolder {
        return MaterialItemHolder.create(parent)
    }

    override fun onBindViewHolder(holder: MaterialItemHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val item = getItem(position)
        holder.bind(item)
    }
}