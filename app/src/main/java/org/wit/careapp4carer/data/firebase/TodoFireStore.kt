package org.wit.careapp4carer.models.firebase

import android.content.Context
import android.graphics.Bitmap
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import org.wit.careapp4carer.models.TodoModel
import org.wit.careapp4carer.models.TodoStore

class TodoFireStore(val context: Context) : TodoStore {
    val todoItems = ArrayList<TodoModel>()
    lateinit var userId: String
    lateinit var db: DatabaseReference

    override fun getAll(): List<TodoModel> {
        return todoItems
    }
    override fun getActiveOnly(): List<TodoModel> {
        val notCompletedItems: List<TodoModel> = todoItems.filter { t -> t.isCompleted }
        return notCompletedItems
    }
    override fun markAsDone(todoItemId: Long) {
        //EP only
    }

    override fun addNewTodoItem(task: TodoModel) {
        db = FirebaseDatabase.getInstance().reference
        userId = "1234567890"
        val key = db.child("users").child(userId).child("ToDoItems").push().key
        key?.let {
            task.id = key
            todoItems.add(task)
            db.child("users").child(userId).child("ToDoItems").child(key).setValue(task)
        }
    }
}
