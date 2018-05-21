package com.smallraw.time.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.smallraw.time.R
import org.jetbrains.annotations.NotNull

class TaskAdapter(@NotNull val res: Int, @NotNull val data: List<String>) : RecyclerView.Adapter<TaskAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(res, null, false)
        return ViewHolder(view);
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvContent: TextView
        val tvTime: TextView
        val tvType: TextView
        val tvDay: TextView
        val tvNumber: TextView
        val tvTypeHint: TextView

        init {
            tvContent = itemView.findViewById(R.id.tv_content)
            tvTime = itemView.findViewById(R.id.tv_time)
            tvType = itemView.findViewById(R.id.content)
            tvDay = itemView.findViewById(R.id.content)
            tvNumber = itemView.findViewById(R.id.content)
            tvTypeHint = itemView.findViewById(R.id.content)
        }
    }
}