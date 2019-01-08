package com.summer.itis.curatorapp.ui.work.progress_card.show

import android.view.ViewGroup
import com.summer.itis.curatorapp.model.step.Step
import com.summer.itis.curatorapp.ui.base.base_first.BaseAdapter


class StepAdapter(items: MutableList<Step>,  private val editListener: StepListView) : BaseAdapter<Step, StepItemHolder>(items) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StepItemHolder {
        return StepItemHolder.create(parent, editListener)
    }

    override fun onBindViewHolder(holder: StepItemHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val item = getItem(position)
        holder.bind(item)
    }
}