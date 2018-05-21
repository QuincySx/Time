package com.smallraw.time.ui.adapter

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.smallraw.time.R
import com.smallraw.time.db.entity.MemorialEntity
import org.jetbrains.annotations.NotNull

class MemorialAdapter(@NotNull val res: Int, @NotNull val data: List<MemorialEntity>) : RecyclerView.Adapter<MemorialAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(res, parent, false)
        return ViewHolder(view);
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context = holder.itemView.context;
        val item = data.get(position)

        holder.tvContent.text = item.name
        holder.tvTime.text = item.beginTime.toString()
        holder.tvDay.text = "天"
        if (item.type == 0) {
            holder.tvType.text = "累计日"
            holder.tvTypeHint.text = "累计"
        } else {
            holder.tvType.text = "倒数日"
            holder.tvTypeHint.text = "剩余"
        }
        holder.tvNumber.text = "2"

        val scale = context.getResources().getDisplayMetrics().density;

        val drawable = GradientDrawable()
        drawable.setColor(Color.parseColor("#${item.color}"))
        drawable.setCornerRadius((6 * scale + 0.5f))

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            holder.itemView.background = drawable
        } else {
            holder.itemView.setBackgroundDrawable(drawable)
        }
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
            tvType = itemView.findViewById(R.id.tv_type)
            tvDay = itemView.findViewById(R.id.tv_day)
            tvNumber = itemView.findViewById(R.id.tv_number)
            tvTypeHint = itemView.findViewById(R.id.tv_type_hint)
        }
    }
}