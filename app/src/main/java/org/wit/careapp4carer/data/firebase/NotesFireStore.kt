package org.wit.careapp4carer.models.firebase

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import org.wit.careapp4carer.models.NotesModel
import org.wit.careapp4carer.models.NotesStore
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class NotesFireStore() : NotesStore {

    private var listOfItems = ArrayList<NotesModel>()
    private var mListOfItems = MutableLiveData<ArrayList<NotesModel>>()
    var count = MutableLiveData<Int>()
    private var db = FirebaseDatabase.getInstance().reference
    private var userId = FirebaseAuth.getInstance().currentUser!!.uid

    override fun add(note: NotesModel) {
        val key = db.child("Users").child(userId).child("Notes").push().key
        key?.let {
            note.id = key
            listOfItems.add(note)
            db.child("Users").child(userId).child("Notes").child(key).setValue(note)
        }
    }

    override fun getActiveNotes(): MutableLiveData<ArrayList<NotesModel>> {
        db.child("Users").child(userId).child("Notes").orderByChild("updatedDate").addValueEventListener(object :
            ValueEventListener {
            override fun onCancelled(dataSnapshot: DatabaseError) {
                Log.w("Database error" , "Retrieving data failed")
            }
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                clear()
                Log.d("snapshot ", "$dataSnapshot")
                dataSnapshot.children.mapNotNullTo(listOfItems) { it.getValue<NotesModel>(
                    NotesModel::class.java) }
                listOfItems.reverse()
                val activeNotes = listOfItems.filter { n -> n.isActive  }
                mListOfItems.postValue(ArrayList(activeNotes))
                count.postValue(activeNotes.size)
            }
        })
        return mListOfItems
    }

    fun getNumberOfNotes(): MutableLiveData<Int> {
        getActiveNotes()
        return count
    }


    override fun getRemovedNotes(): MutableLiveData<ArrayList<NotesModel>> {
        db.child("Users").child(userId).child("Notes").orderByChild("updatedDate")
            .addValueEventListener(object :
                ValueEventListener {
                override fun onCancelled(dataSnapshot: DatabaseError) {
                    Log.w("Database error", "Retrieving data failed")
                }

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    clear()
                    Log.d("snapshot ", "$dataSnapshot")
                    dataSnapshot.children.mapNotNullTo(listOfItems) {
                        it.getValue<NotesModel>(
                            NotesModel::class.java
                        )
                    }
                    listOfItems.reverse()
                    val activeNotes = listOfItems.filter { n -> !n.isActive }
                    mListOfItems.postValue(ArrayList(activeNotes))
                }
            })
        return mListOfItems
    }

    override fun edit(note: NotesModel){
        val noteId = note.id
        Log.d("firebase update", "$note")
        db.child("Users").child(userId).child("Notes").child(noteId).setValue(note)
    }

    override fun remove(noteId: String) {
        val now = getDate()
        db.child("Users").child(userId).child("Notes").child(noteId).child("active").setValue(false)
        db.child("Users").child(userId).child("Notes").child(noteId).child("updatedBy").setValue("Carer")
        db.child("Users").child(userId).child("Notes").child(noteId).child("updatedDate").setValue(now)
    }

    fun getDate() :  String {
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        return current.format(formatter)
    }

    fun clear() {
        listOfItems.clear()
    }

}