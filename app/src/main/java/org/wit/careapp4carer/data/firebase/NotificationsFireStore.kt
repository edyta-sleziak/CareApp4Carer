package org.wit.careapp4carer.models.firebase

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.listitem_notification.view.*
import org.wit.careapp4carer.models.NotificationsModel
import org.wit.careapp4carer.models.NotificationsStore
import org.wit.careapp4carer.models.TodoModel
import java.util.Collections.addAll
import kotlin.concurrent.timerTask

class NotificationsFireStore() : NotificationsStore {

    private var listOfItems = ArrayList<NotificationsModel>()
    private var activeNotificationsCount = MutableLiveData<Int>()
    private var completedNotificationsCount = MutableLiveData<Int>()
    private var mListOfItems = MutableLiveData<ArrayList<NotificationsModel>>()
    private var db = FirebaseDatabase.getInstance().reference
    private var userId = FirebaseAuth.getInstance().currentUser!!.uid



    fun getCompletedNotification(): MutableLiveData<ArrayList<NotificationsModel>> {
        db.child("Users").child(userId).child("Notifications").addValueEventListener(object :
            ValueEventListener {
            override fun onCancelled(dataSnapshot: DatabaseError) {
                Log.w("Database error" , "Retrieving data failed")
            }
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                clear()
                Log.d("snapshot ", "$dataSnapshot")
                dataSnapshot.children.mapNotNullTo(listOfItems) { it.getValue<NotificationsModel>(
                    NotificationsModel::class.java) }
                val completedNotifications = listOfItems.filter { n -> n.completedTime != "Not completed" }
                mListOfItems.postValue(ArrayList(completedNotifications))
                completedNotificationsCount.postValue(completedNotifications.size)
            }
        })
        return mListOfItems
    }

    fun getActiveNotification(): MutableLiveData<ArrayList<NotificationsModel>> {
        db.child("Users").child(userId).child("Notifications").addValueEventListener(object :
            ValueEventListener {
            override fun onCancelled(dataSnapshot: DatabaseError) {
                Log.w("Database error" , "Retrieving data failed")
            }
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                clear()
                Log.d("snapshot ", "$dataSnapshot")
                dataSnapshot.children.mapNotNullTo(listOfItems) { it.getValue<NotificationsModel>(
                    NotificationsModel::class.java) }
                val activeNotifications = listOfItems.filter { n -> n.completedTime == "Not completed" }
                mListOfItems.postValue(ArrayList(activeNotifications))
                activeNotificationsCount.postValue(activeNotifications.size)
            }
        })
        return mListOfItems
    }

    fun getActiveNotificationCount(): MutableLiveData<Int> {
        getActiveNotification()
        return activeNotificationsCount
    }

    fun getCOmpletedNotificationCount(): MutableLiveData<Int> {
        getCompletedNotification()
        return completedNotificationsCount
    }

    override fun displayNotification(notificationId: String) {

    }

    override fun addNewNotification(notification: NotificationsModel) {
        val key = db.child("Users").child(userId).child("Notifications").push().key
        key?.let {
            notification.id = key
            notification.userId = userId
            listOfItems.add(notification)
            db.child("Users").child(userId).child("Notifications").child(key).setValue(notification)
        }
    }

    fun editNotification(notification: NotificationsModel) {
        val notificationId = notification.id
        Log.d("firebase update", "$notification")
        db.child("Users").child(userId).child("Notifications").child(notificationId).setValue(notification)
    }

    fun clear() {
        listOfItems.clear()
    }


    override fun removeNotification(notificationId: String) {
        db.child("Users").child(userId).child("Notifications").child(notificationId).removeValue()
    }
}