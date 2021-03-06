package com.smallraw.time.ui.adapter

import android.annotation.SuppressLint
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
import com.smallraw.time.utils.dateFormat
import com.smallraw.time.utils.dateParse
import com.smallraw.time.utils.differentDays
import com.smallraw.time.utils.getWeekOfDate
import org.jetbrains.annotations.NotNull
import java.util.*


class MemorialAdapter(@NotNull val res: Int, @NotNull val data: List<MemorialEntity>) : RecyclerView.Adapter<MemorialAdapter.ViewHolder>() {
    public var onItemLongClickListener: OnItemLongClickListener? = null
    public var onItemClickListener: OnItemClickListener? = null
    val mCurrentDate = dateParse(dateFormat(Date()))

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(res, parent, false)
        return ViewHolder(view);
    }

    override fun getItemCount(): Int {
        return data.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context = holder.itemView.context;
        val item = data.get(position)

        if (item.type == 0) {
            holder.tvWeek.text = getWeekOfDate(context, item.beginTime)
        } else {
            holder.tvWeek.text = "至"
        }
        holder.tvContent.text = item.name
        holder.tvTime.text = dateFormat(item.beginTime)

        holder.tvDay.text = "天"

        if (item.type == 0) {
            val days = differentDays(Date(), item.beginTime)
            holder.tvNumber.text = "${Math.abs(days)}"
            holder.tvType.text = "累计日"
            holder.tvTypeHint.text = "累计"
        } else {
            holder.tvType.text = "倒数日"
            if (mCurrentDate < item.beginTime) {
                val days = differentDays(Date(), item.beginTime)
                holder.tvNumber.text = "${Math.abs(days)}"
                holder.tvTypeHint.text = "剩余"
            } else if (mCurrentDate <= item.endTime || (item.endTime == item.beginTime && mCurrentDate == item.beginTime)) {
                val days = differentDays(Date(), item.beginTime)
                holder.tvNumber.text = "${Math.abs(days + 1)}"
                holder.tvTypeHint.text = "活动中"
            } else {
                val days = differentDays(item.endTime, Date())
                holder.tvNumber.text = "${Math.abs(days)}"
                holder.tvTypeHint.text = "已过"
            }
        }

        val scale = context.getResources().getDisplayMetrics().density;

        val drawable = GradientDrawable()
        drawable.setColor(Color.parseColor(item.color))
        drawable.setCornerRadius((8 * scale + 0.5f))

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            holder.itemView.background = drawable
        } else {
            holder.itemView.setBackgroundDrawable(drawable)
        }
        holder.itemView.setOnLongClickListener {
            if (onItemLongClickListener != null) {
                onItemLongClickListener!!.onLongClick(holder.adapterPosition, holder, item)
            }
            false
        }

        holder.itemView.setOnClickListener {
            if (onItemClickListener != null) {
                onItemClickListener!!.onClick(holder.adapterPosition, holder, item)
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvContent: TextView
        val tvTime: TextView
        val tvWeek: TextView
        val tvType: TextView
        val tvDay: TextView
        val tvNumber: TextView
        val tvTypeHint: TextView

        init {
            tvContent = itemView.findViewById(R.id.tv_content)
            tvTime = itemView.findViewById(R.id.tv_time)
            tvWeek = itemView.findViewById(R.id.tv_week)
            tvType = itemView.findViewById(R.id.tv_type)
            tvDay = itemView.findViewById(R.id.tv_day)
            tvNumber = itemView.findViewById(R.id.tv_number)
            tvTypeHint = itemView.findViewById(R.id.tv_type_hint)
        }
    }
}