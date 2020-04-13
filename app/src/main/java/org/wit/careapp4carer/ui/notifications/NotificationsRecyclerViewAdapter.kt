package org.wit.careapp4carer.ui.notifications

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import org.wit.careapp4carer.R
import org.wit.careapp4carer.models.NotificationsModel
import org.wit.careapp4carer.models.firebase.NotificationsFireStore

class NotificationsRecyclerViewAdapter(var notificationsList: List<NotificationsModel>
) : RecyclerView.Adapter<NotificationsRecyclerViewAdapter.MainHolder>() {

    private var mContext: Context? = null
    private var dbNotificationsList = NotificationsFireStore()

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
        val mNotificationsListItem = notificationsList!!.get(position)

        if(mNotificationsListItem.notification != null) {
            holder.notificationName.setText(mNotificationsListItem.notification)
        }

        if(mNotificationsListItem.completedTime != null) {
            holder.notificationCompletedDate.setText(mNotificationsListItem.completedTime)
        }

        if(mNotificationsListItem.displayDate != null) {
            holder.notificationDisplayDate.setText(mNotificationsListItem.displayDate)
        }

        if(mNotificationsListItem.displayTime != null) {
            holder.notificationDisplayTime.setText(mNotificationsListItem.displayTime)
        }

        holder.buttonDelete.setOnClickListener{
            dbNotificationsList.removeNotification(mNotificationsListItem.id)
            notifyDataSetChanged()
        }

        holder.itemView.setOnClickListener{
            Log.d("CLICKED:", " clicked on item ${mNotificationsListItem?.notification}")
            var action : NotificationsFragmentDirections.ActionNavNotificationsToAddNotificationFragment = NotificationsFragmentDirections.actionNavNotificationsToAddNotificationFragment(mNotificationsListItem)
            it.findNavController().navigate(action)
            //TODO edit data (pass mNotificationListItem as parameter
        }



    }

    override fun getItemCount(): Int = notificationsList!!.size

    class MainHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val notificationName: TextView = itemView.findViewById(R.id.notificationName)
        val notificationCompletedDate: TextView = itemView.findViewById(R.id.notificationCompletedDate)
        val notificationDisplayDate: TextView = itemView.findViewById(R.id.notificationDisplayDate)
        val notificationDisplayTime: TextView = itemView.findViewById(R.id.notificationDisplayTime)
        val buttonDelete: ImageView = itemView.findViewById(R.id.button_deleteNotification)

    }

}