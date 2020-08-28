package org.wit.careapp4carer.models.firebase

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import org.jetbrains.anko.AnkoLogger
import org.wit.careapp4carer.models.HrModel
import org.wit.careapp4carer.models.HrStore

class HrFireStore() : HrStore, AnkoLogger {

    var hrRecords = ArrayList<HrModel>()
    var latestHr = ArrayList<HrModel>()
    val hrRecordsMut = MutableLiveData<ArrayList<HrModel>>()
    val latestHrMut = MutableLiveData<HrModel>()
    val auth = FirebaseDatabase.getInstance()
    private var db = auth.reference

    override fun getHrHistory() : MutableLiveData<ArrayList<HrModel>> {
        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        db.child("Users").child(userId).child("HrHistory").orderByChild("dateTime").addValueEventListener(object :
            ValueEventListener {
            override fun onCancelled(dataSnapshot: DatabaseError) {
                Log.w("Database error" , "Retrieving data failed")
            }
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                hrRecords.clear()
                dataSnapshot.children.mapNotNullTo(hrRecords) { it.getValue<HrModel>(
                    HrModel::class.java) }
                hrRecords.reverse()
                hrRecordsMut.postValue(hrRecords)
            }
        })
        return hrRecordsMut
    }

    override fun getLatestHr() : MutableLiveData<HrModel> {
        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        db.child("Users").child(userId).child("LatestActivity").child("LatestHr").addValueEventListener(object :
            ValueEventListener {
            override fun onCancelled(dataSnapshot: DatabaseError) {
                Log.w("Database error" , "Retrieving data failed")
            }
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                latestHr.clear()
                Log.d("snapshot",dataSnapshot.toString())
                dataSnapshot.children.mapNotNullTo(latestHr) { it.getValue<HrModel>(
                    HrModel::class.java) }

                latestHrMut.postValue(latestHr[0])
            }
        })
        return latestHrMut
    }


}