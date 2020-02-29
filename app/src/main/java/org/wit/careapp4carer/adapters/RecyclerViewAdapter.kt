package org.wit.careapp4carer.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.listitem_todo.view.*
import org.wit.careapp4carer.R
import org.wit.careapp4carer.models.TodoModel

interface ToDoListener {
    fun onTaskClick(task: TodoModel)
}

class RecyclerViewAdapter constructor(private var todolist: List<TodoModel>
) : RecyclerView.Adapter<RecyclerViewAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(
            LayoutInflater.from(parent?.context).inflate(
                R.layout.listitem_todo,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val todolist = todolist[holder.adapterPosition]
        holder.bind(todolist)
    }

    override fun getItemCount(): Int = todolist.size

    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(task: TodoModel) {
            itemView.todoItemName.text = task.task
            itemView.todoCompletedDate.text = task.dateCompleted.toString()
        }
    }

}