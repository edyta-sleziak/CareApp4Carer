package org.wit.careapp4carer.models

import androidx.lifecycle.MutableLiveData

interface TodoStore {
    fun getAll(): ArrayList<TodoModel>
    fun getActiveOnly(): List<TodoModel>
    fun markAsDone(todoItemId: Long)
    fun addNewTodoItem(task: TodoModel)
    fun clear()
}