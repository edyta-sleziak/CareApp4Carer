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
import org.wit.careapp.models.SosModel
import org.wit.careapp.models.SosStore
import org.wit.careapp4carer.models.NotificationsModel
import org.wit.careapp4carer.models.NotificationsStore
import org.wit.careapp4carer.models.TodoModel
import java.util.Collections.addAll
import kotlin.concurrent.timerTask

class SosFireStore() : SosStore {

    private var listOfItems = ArrayList<SosModel>()
    private var mListOfItems = MutableLiveData<SosModel>()
    private var db = FirebaseDatabase.getInstance().reference
    private var userId = FirebaseAuth.getInstance().currentUser!!.uid

    override fun getData(id: String): MutableLiveData<SosModel> {
        db.child("Users").child(userId).child("SOS").orderByChild("dateTime").addValueEventListener(object :
            ValueEventListener {
            override fun onCancelled(dataSnapshot: DatabaseError) {
                Log.w("Database error" , "Retrieving data failed")
            }
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                listOfItems.clear()
                Log.d("snapshot ", "$dataSnapshot")
                dataSnapshot.children.mapNotNullTo(listOfItems) { it.getValue<SosModel>(
                    SosModel::class.java) }
                listOfItems.reverse()
                val selectedAlert = listOfItems.filter { n -> n.id == id }
                mListOfItems.postValue(selectedAlert[0])
            }
        })
        return mListOfItems
    }
}