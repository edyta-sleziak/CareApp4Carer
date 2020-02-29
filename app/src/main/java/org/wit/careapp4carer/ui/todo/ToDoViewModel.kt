package org.wit.careapp4carer.ui.todo

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.FirebaseDatabase
import org.wit.careapp4carer.models.TodoModel
import org.wit.careapp4carer.models.TodoStore
import org.wit.careapp4carer.models.firebase.TodoFireStore

class ToDoViewModel : ViewModel()  {

    private lateinit var todoList: TodoStore
    private var mToDoList = MutableLiveData<List<TodoModel>>()

    fun getToDoList(): LiveData<List<TodoModel>> {
        return mToDoList
    }

    fun addToDoItem(string: String) {
        var newToDoItem = TodoModel("123",string, false, null,0)
        todoList.addNewTodoItem(newToDoItem)
    }



}