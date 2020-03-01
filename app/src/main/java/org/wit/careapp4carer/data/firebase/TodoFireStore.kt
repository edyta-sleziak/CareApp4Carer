package org.wit.careapp4carer.models.firebase

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import org.wit.careapp4carer.models.AccountInfoModel
import org.wit.careapp4carer.models.TodoModel
import org.wit.careapp4carer.models.TodoStore

class TodoFireStore() : TodoStore {
    private var todoItems = ArrayList<TodoModel>()
    private var todoItemsMut = MutableLiveData<ArrayList<TodoModel>>()
    private var db = FirebaseDatabase.getInstance().reference
    private var userId = FirebaseAuth.getInstance().currentUser!!.uid

    override fun getAll(): ArrayList<TodoModel> {
        fetchData()
        return todoItems
    }

    fun getMutalbleLiveData():MutableLiveData<ArrayList<TodoModel>> {
        fetchData()
        todoItemsMut.value = todoItems
        return todoItemsMut
    }

    override fun getActiveOnly(): List<TodoModel> {
        val notCompletedItems: List<TodoModel> = todoItems.filter { t -> t.isCompleted }
        return notCompletedItems
    }
    override fun markAsDone(todoItemId: Long) {
        //EP only
    }

    override fun addNewTodoItem(task: TodoModel) {
        val key = db.child("Users").child(userId).child("ToDoItems").push().key
        key?.let {
            task.id = key
            todoItems.add(task)
            db.child("Users").child(userId).child("ToDoItems").child(key).setValue(task)
        }
    }

    override fun clear() {
        todoItems.clear()
    }

    fun fetchData() {
        db.child("Users").child(userId).child("ToDoItems").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(dataSnapshot: DatabaseError) {
                Log.w("Database error" , "Retrieving data failed")
            }
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                Log.d("snapshot ", "$dataSnapshot")
                dataSnapshot.children.mapNotNullTo(todoItems) { it.getValue<TodoModel>(
                    TodoModel::class.java) }
                Log.d("saved data", "$todoItems")
            }
        })
    }
}
