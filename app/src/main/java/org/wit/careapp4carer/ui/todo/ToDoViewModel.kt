package org.wit.careapp4carer.ui.todo

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import org.wit.careapp4carer.models.TodoModel
import org.wit.careapp4carer.models.TodoStore
import org.wit.careapp4carer.models.firebase.TodoFireStore

class ToDoViewModel : ViewModel()  {

    val todoFireStore = TodoFireStore()
    val mToDoList: LiveData<ArrayList<TodoModel>> get() = todoFireStore.getMutalbleLiveData()



    fun getToDoList(): LiveData<ArrayList<TodoModel>> {
        return mToDoList
    }




}