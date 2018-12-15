package com.summer.itis.curatorapp.ui.work.progress_card

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


import com.summer.itis.curatorapp.R
import com.summer.itis.curatorapp.model.step.Step
import com.summer.itis.curatorapp.utils.FormatterUtil
import kotlinx.android.synthetic.main.item_step.view.*


class StepItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(item: Step) {
        itemView.tv_title.text = item.title
        itemView.tv_date.text = FormatterUtil.getStringFromDate(item.dateFinish)
    }


    companion object {

        fun create(parent: ViewGroup): StepItemHolder {
            val view =  LayoutInflater.from(parent.context).inflate(R.layout.item_step, parent, false);
            val holder = StepItemHolder(view)
            return holder
        }
    }
}
