package org.wit.careapp4carer.ui.notifications

import android.app.Notification
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_notifications.view.*
import org.wit.careapp4carer.R
import org.wit.careapp4carer.models.NotificationsModel

class NotificationsRecyclerViewAdapter(var notificationsList: List<NotificationsModel>
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
        val mNotificationsList = notificationsList!!.get(position)

        if(mNotificationsList.notification != null) {
            holder.notificationName.setText(mNotificationsList.notification)
        }

        if(mNotificationsList.completedTime != null) {
            holder.notificationCompletedDate.setText(mNotificationsList.completedTime.toString())
        }

        if(mNotificationsList.displayTime != null) {
            holder.notificationDisplayDate.setText(mNotificationsList.displayTime.toString())
        }

        holder.notificationName.setOnClickListener{
            Log.d("CLICKED", "You clicked on task ${holder.notificationName}")
        }


    }

    override fun getItemCount(): Int = notificationsList!!.size

    class MainHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val notificationName: TextView = itemView.findViewById(R.id.notificationName)
        val notificationCompletedDate: TextView = itemView.findViewById(R.id.notificationCompletedDate)
        val notificationDisplayDate: TextView = itemView.findViewById(R.id.notificationDisplayDate)

    }

}