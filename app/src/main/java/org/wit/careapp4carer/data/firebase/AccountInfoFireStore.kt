package org.wit.careapp4carer.models.firebase

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseError
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.toast
import org.wit.careapp4carer.models.*
import kotlin.concurrent.thread

class AccountInfoFireStore() : AccountInfoStore, AnkoLogger {

    var accounts = ArrayList<AccountInfoModel>()
    val accountData = MutableLiveData<AccountInfoModel>()
    val auth = FirebaseDatabase.getInstance()
    private var db = auth.reference

    fun getData() : MutableLiveData<AccountInfoModel> {
        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        db.child("Users").child(userId).child("Settings").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(dataSnapshot: DatabaseError) {
                Log.w("Database error" , "Retrieving data failed")
            }
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                accounts.clear()
                Log.d("snapshot accounts ", "$dataSnapshot")

                dataSnapshot.children.mapNotNullTo(accounts) { it.getValue<AccountInfoModel>(
                    AccountInfoModel::class.java) }

                Log.d("Accounts field", "$accounts")
                accountData.postValue(accounts[0])
            }
        })
        return accountData
    }

    fun add(newAccount: AccountInfoModel) {
        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        db.child("Users").child(userId).child("Settings").child("AccountInfo").setValue(newAccount)
        db.child("Users").child(userId).child("LatestActivity").child("LatestHr").child("data").setValue(
            HrModel(0,"")
        )
        db.child("Users").child(userId).child("LatestActivity").child("LatestLocation").child("data").setValue(
            LocationModel(0.0,0.0,5f,"")
        )
    }

    fun addToken(token: String) {
        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        db.child("Users").child(userId).child("Settings").child("AccountInfo").child("registrationTokenCarer").setValue(token)
    }

    fun getUser(): String? {
        return FirebaseAuth.getInstance().currentUser?.uid
    }

    fun updateAccountInDb(accountName: String, carerName: String, patientName: String) {
        val userId = FirebaseAuth.getInstance().currentUser!!.uid

        db.child("Users").child(userId).child("Settings").child("AccountInfo").child("email").setValue(accountName)
        db.child("Users").child(userId).child("Settings").child("AccountInfo").child("carerName").setValue(carerName)
        db.child("Users").child(userId).child("Settings").child("AccountInfo").child("epName").setValue(patientName)
    }

    fun updateApplicationInDb(sosContactNumber: String,saveHrRangeLow: String, saveHrRangeHigh: String,saveHomeDistance: String,dailyStepsGoal: String,notificationResponseTime: String) {
        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        db.child("Users").child(userId).child("Settings").child("AccountInfo").child("sosContactNumber").setValue(sosContactNumber)
        db.child("Users").child(userId).child("Settings").child("AccountInfo").child("saveHrRangeLow").setValue(saveHrRangeLow)
        db.child("Users").child(userId).child("Settings").child("AccountInfo").child("saveHrRangeHigh").setValue(saveHrRangeHigh)
        db.child("Users").child(userId).child("Settings").child("AccountInfo").child("saveHomeDistance").setValue(saveHomeDistance)
        db.child("Users").child(userId).child("Settings").child("AccountInfo").child("dailyStepsGoal").setValue(dailyStepsGoal)
        db.child("Users").child(userId).child("Settings").child("AccountInfo").child("notificationResponseTime").setValue(notificationResponseTime)
    }

    fun saveHomeLocation(location: Location) {
        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        db.child("Users").child(userId).child("Settings").child("AccountInfo").child("location").setValue(location)
    }

}