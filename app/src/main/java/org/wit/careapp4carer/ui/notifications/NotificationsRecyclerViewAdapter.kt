package org.wit.careapp4carer.ui.notifications

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import org.jetbrains.anko.internals.AnkoInternals.getContext
import org.wit.careapp4carer.R
import org.wit.careapp4carer.models.NotificationsModel
import org.wit.careapp4carer.models.firebase.NotificationsFireStore

class NotificationsRecyclerViewAdapter(var notificationsList: List<NotificationsModel>
) : RecyclerView.Adapter<NotificationsRecyclerViewAdapter.MainHolder>() {

    private var mContext: Context? = null
    private var dbNotificationsList = NotificationsFireStore()

    val swipeToDeleteCallback = object : SwipeToDeleteCallback() {
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val pos = viewHolder.adapterPosition
//            dbNotificationsList.removeNotification(notificationsList.get(pos).id)
//            notifyDataSetChanged()
//            notifyItemRemoved(pos)
//            notifyItemRangeChanged(pos, notificationsList.size)

            if (direction == ItemTouchHelper.LEFT) {
                //val deletedModel = imageModelArrayList!![pos]
                dbNotificationsList.removeNotification(notificationsList.get(pos).id)
            } else {
                var newNotification = NotificationsModel("", notificationsList!!.get(pos).notification, "", "")
                var action : NotificationsFragmentDirections.ActionNavNotificationsToAddNotificationFragment = NotificationsFragmentDirections.actionNavNotificationsToAddNotificationFragment(newNotification)
                viewHolder.itemView.findNavController().navigate(action)
            }
        }
    }

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

//        holder.buttonDelete.setOnClickListener{
//            dbNotificationsList.removeNotification(mNotificationsListItem.id)
//            notifyDataSetChanged()
//        }

        holder.itemView.setOnClickListener{
            Log.d("CLICKED:", " clicked on item ${mNotificationsListItem?.notification}")
            val action : NotificationsFragmentDirections.ActionNavNotificationsToAddNotificationFragment = NotificationsFragmentDirections.actionNavNotificationsToAddNotificationFragment(mNotificationsListItem)
            it.findNavController().navigate(action)
        }



    }

    override fun getItemCount(): Int = notificationsList!!.size

    class MainHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val notificationName: TextView = itemView.findViewById(R.id.notificationName)
        val notificationCompletedDate: TextView = itemView.findViewById(R.id.notificationCompletedDate)
        val notificationDisplayDate: TextView = itemView.findViewById(R.id.notificationDisplayDate)
        val notificationDisplayTime: TextView = itemView.findViewById(R.id.notificationDisplayTime)
        //val buttonDelete: ImageView = itemView.findViewById(R.id.button_deleteNotification)

    }


    abstract class SwipeToDeleteCallback : ItemTouchHelper.Callback() {

        override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
            val swipeFlag = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            return makeMovementFlags(0, swipeFlag)
        }

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        override fun onChildDraw(
            c: Canvas,
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            dX: Float,
            dY: Float,
            actionState: Int,
            isCurrentlyActive: Boolean
        ) {
            val icon: Bitmap
            if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {

                val itemView = viewHolder.itemView
                val height = itemView.bottom.toFloat() - itemView.top.toFloat()
                val width = height / 3
                val p = Paint()

                if (dX > 0) {
                    p.color = Color.parseColor("#388E3C")
                    val background =
                        RectF(
                            itemView.left.toFloat(),
                            itemView.top.toFloat(),
                            dX,
                            itemView.bottom.toFloat()
                        )
                    c.drawRect(background, p)
                    icon = BitmapFactory.decodeResource(itemView.resources, R.drawable.duplicate)
                    val icon_dest = RectF(
                        itemView.left.toFloat() + width,
                        itemView.top.toFloat() + width,
                        itemView.left.toFloat() + 2 * width,
                        itemView.bottom.toFloat() - width
                    )
                    c.drawBitmap(icon, null, icon_dest, p)
                } else {
                    p.color = Color.parseColor("#D32F2F")
                    val background = RectF(
                        itemView.right.toFloat() + dX,
                        itemView.top.toFloat(),
                        itemView.right.toFloat(),
                        itemView.bottom.toFloat()
                    )
                    c.drawRect(background, p)
                    icon = BitmapFactory.decodeResource(itemView.context.resources, R.drawable.delete)
                    val icon_dest = RectF(
                        itemView.right.toFloat() - 2 * width,
                        itemView.top.toFloat() + width,
                        itemView.right.toFloat() - width,
                        itemView.bottom.toFloat() - width
                    )
                    c.drawBitmap(icon, null, icon_dest, p)
                }
                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
            }

        }

    }

}