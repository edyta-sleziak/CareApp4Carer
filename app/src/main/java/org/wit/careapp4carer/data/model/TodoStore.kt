package org.wit.careapp4carer.models

interface TodoStore {
    fun getAll(): List<TodoModel>
    fun getActiveOnly(): List<TodoModel>
    fun markAsDone(todoItemId: Long)
    fun addNewTodoItem(task: TodoModel)
}