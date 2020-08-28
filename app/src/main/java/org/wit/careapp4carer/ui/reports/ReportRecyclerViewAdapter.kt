package org.wit.careapp4carer.ui.reports

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.wit.careapp4carer.R
import org.wit.careapp4carer.models.HrModel

class ReportRecyclerViewAdapter(var hrList: List<HrModel>
) : RecyclerView.Adapter<ReportRecyclerViewAdapter.MainHolder>() {

    private var mContext: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        this.mContext=parent.context
        return MainHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.listitem_hr,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val mListItem = hrList!!.get(position)

        if(mListItem.dateTime != null) {
            holder.date.setText(mListItem.dateTime)
        }

        if(mListItem.hrValue != null) {
            holder.value.setText(mListItem.hrValue.toString() + " bpm")
        }

    }

    override fun getItemCount(): Int = hrList!!.size

    class MainHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val date: TextView = itemView.findViewById(R.id.hr_date)
        val value: TextView = itemView.findViewById(R.id.hr_value)
    }

}