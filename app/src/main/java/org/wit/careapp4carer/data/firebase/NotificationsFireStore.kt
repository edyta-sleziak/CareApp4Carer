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
import kotlin.concurrent.timerTask

class NotificationsFireStore() : NotificationsStore {

    private var listOfItems = ArrayList<NotificationsModel>()
    private var activeNotificationsCount: Int = 0
    private var completedNotificationsCountInt = 0
    private var mListOfItems = MutableLiveData<ArrayList<NotificationsModel>>()
    private var db = FirebaseDatabase.getInstance().reference
    private var userId = FirebaseAuth.getInstance().currentUser!!.uid

    override fun getAll(): ArrayList<NotificationsModel> {
        fetchData()
        return listOfItems
    }

    fun getCompletedNotification(): MutableLiveData<ArrayList<NotificationsModel>> {
        fetchData()
        val completedNotifications = listOfItems.filter { n -> n.completedTime != "Not completed" }
        mListOfItems.value = ArrayList(completedNotifications)
        return mListOfItems
    }

    fun getActiveNotification(): MutableLiveData<ArrayList<NotificationsModel>> {
        fetchData()
        val activeNotifications = listOfItems.filter { n -> n.completedTime == "Not completed" }
        mListOfItems.value = ArrayList(activeNotifications)
        return mListOfItems
    }

    fun getActiveNotificationCount(): Int {
        fetchData()
        val list = listOfItems.filter { n -> n.completedTime == "Not completed" }
        activeNotificationsCount = list.size
        Log.d("filtered ACTIVE", "${list}")
        Log.d("listOfItems in getActiveNotificationCount", "${listOfItems}")
        Log.d("filtered ACTIVE size", "${list.size}")
        return activeNotificationsCount
    }

    fun getCOmpletedNotificationCount(): Int {
        fetchData()
        val list = listOfItems.filter { n -> n.completedTime != "Not completed" }
        completedNotificationsCountInt = list.size
        Log.d("list COMPLETED", "${list.size}")
        Log.d("list COMPLETED", "${list}")
        return completedNotificationsCountInt
    }

    fun getMutalbleLiveData(): MutableLiveData<ArrayList<NotificationsModel>> {
        fetchData()
        mListOfItems.value = listOfItems
        return mListOfItems
    }

    override fun displayNotification(noteId: Long) {

    }
    override fun markAsDone(noteId: Long) {

    }
    override fun displayLater(noteId: Long) {

    }



    override fun addNewNotification(notification: NotificationsModel) {
        val key = db.child("Users").child(userId).child("Notifications").push().key
        key?.let {
            notification.id = key
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

    fun fetchData() {
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
                Log.d("list ", "$listOfItems")
            }
        })
    }
}