package org.wit.careapp4carer.models.firebase

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import org.jetbrains.anko.AnkoLogger
import org.wit.careapp4carer.models.*

class LocationFireStore() : LocationStore, AnkoLogger {

    var locations = ArrayList<LocationModel>()
    var latestLocation = ArrayList<LocationModel>()
    val locationData = MutableLiveData<ArrayList<LocationModel>>()
    val latestLocationDataMut = MutableLiveData<LocationModel>()
    val auth = FirebaseDatabase.getInstance()
    private var db = auth.reference

    override fun getLocationHistory() : MutableLiveData<ArrayList<LocationModel>> {
        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        db.child("Users").child(userId).child("LocationHistory").addValueEventListener(object :
            ValueEventListener {
            override fun onCancelled(dataSnapshot: DatabaseError) {
                Log.w("Database error" , "Retrieving data failed")
            }
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                locations.clear()
                dataSnapshot.children.mapNotNullTo(locations) { it.getValue<LocationModel>(
                    LocationModel::class.java) }
                locationData.postValue(locations)
            }
        })
        return locationData
    }

    override fun getLatestLocation() : MutableLiveData<LocationModel> {
        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        db.child("Users").child(userId).child("LatestActivity").child("LatestLocation").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(dataSnapshot: DatabaseError) {
                Log.w("Database error" , "Retrieving data failed")
            }
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                latestLocation.clear()
                Log.d("snapshot",dataSnapshot.toString())
                dataSnapshot.children.mapNotNullTo(latestLocation) { it.getValue<LocationModel>(
                    LocationModel::class.java) }

                latestLocationDataMut.postValue(latestLocation[0])
            }
        })
        return latestLocationDataMut
    }

    override fun add(location: LocationModel) {
        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        db.child("Users").child(userId).child("LatestActivity").child("LatestLocation").setValue(location)
    }


}