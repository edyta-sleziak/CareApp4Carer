package org.wit.careapp4carer.ui.notifications

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.wit.careapp4carer.R
import org.wit.careapp4carer.models.NotificationsModel

class NotificationsRecyclerViewAdapter(var notificationlist: List<NotificationsModel>
) : RecyclerView.Adapter<NotificationsRecyclerViewAdapter.MainHolder>() {

    private var mContext: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        this.mContext=parent.context
        return MainHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.listitem_notification,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val mNotifications = notificationlist!!.get(position)

    }

    override fun getItemCount(): Int = notificationlist!!.size

    class MainHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val notificationText: TextView = itemView.findViewById(R.id.notification_text)
        val notificationDate: TextView = itemView.findViewById(R.id.notification_date)

    }

}