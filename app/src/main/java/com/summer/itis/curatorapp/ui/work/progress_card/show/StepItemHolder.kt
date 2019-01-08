package com.summer.itis.curatorapp.ui.work.progress_card.show

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


import com.summer.itis.curatorapp.R
import com.summer.itis.curatorapp.model.step.Step
import com.summer.itis.curatorapp.utils.FormatterUtil
import kotlinx.android.synthetic.main.item_step.view.*


class StepItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private lateinit var listener: StepListView


    fun bind(item: Step) {
        itemView.tv_title.text = item.title
        itemView.tv_date.text = FormatterUtil.getStringFromDate(item.dateFinish)
        showStatus(item)
        itemView.iv_check_status.setOnClickListener {
            changeStatus(item)
        }

    }

    private fun showStatus(item: Step) {
        when (item.status.id) {

            "1" -> {

            }

            "2" -> {
                itemView.iv_check_status.setImageResource(R.drawable.ic_radio_button_unchecked_black_24dp)
            }

            "3" -> {
                itemView.iv_check_status.setImageResource(R.drawable.ic_check_circle_black_24dp)
            }
        }
    }

    private fun changeStatus(item: Step) {
        when (item.status.id) {

            "2" -> {
                item.status.id = "3"
            }

            "3" -> {
                item.status.id = "2"
            }
        }
        showStatus(item)
        listener.changeStatus(adapterPosition, item.status)
    }

    companion object {

        fun create(parent: ViewGroup, listener: StepListView): StepItemHolder {
            val view =  LayoutInflater.from(parent.context).inflate(R.layout.item_step, parent, false);
            val holder = StepItemHolder(view)
            holder.listener = listener
            return holder
        }
    }
}
