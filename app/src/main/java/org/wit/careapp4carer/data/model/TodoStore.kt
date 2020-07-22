package org.wit.careapp4carer.models

import androidx.lifecycle.MutableLiveData

interface TodoStore {
    fun getActiveOnly(): MutableLiveData<ArrayList<TodoModel>>
    fun getCompletedOnly(): MutableLiveData<ArrayList<TodoModel>>
    fun remove(taskId: String)
    fun edit(task: TodoModel)
    fun addNewTodoItem(task: TodoModel)
    fun clear()
}