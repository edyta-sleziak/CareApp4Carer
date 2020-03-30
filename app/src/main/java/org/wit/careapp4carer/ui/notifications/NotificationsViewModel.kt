package org.wit.careapp4carer.ui.notifications

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import org.wit.careapp4carer.models.NotificationsModel
import org.wit.careapp4carer.models.firebase.NotificationsFireStore

class NotificationsViewModel : ViewModel()  {

    val notificationsFireStore = NotificationsFireStore()
    val mItemsList: LiveData<ArrayList<NotificationsModel>> get() = notificationsFireStore.getMutalbleLiveData()



    fun getNotificationsList(): LiveData<ArrayList<NotificationsModel>> {
        return mItemsList
    }




}