package org.wit.careapp4carer.ui.todo

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.listitem_todo.view.*
import org.wit.careapp4carer.R
import org.wit.careapp4carer.models.TodoModel

class ToDoRecyclerViewAdapter(var todolist: List<TodoModel>
) : RecyclerView.Adapter<ToDoRecyclerViewAdapter.MainHolder>() {

    private var mContext: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        this.mContext=parent.context
        return MainHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.listitem_todo,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val mToDoList = todolist!!.get(position)

        if(mToDoList.task != null) {
            holder.todoItemName.setText(mToDoList.task)
        }

        if(mToDoList.dateCompleted != null) {
            holder.todoCompletedDate.setText(mToDoList.dateCompleted.toString())
        }

        holder.todoItemName.setOnClickListener{
            Log.d("CLICKED", "You clicked on task ${holder.todoItemName}")
        }


    }

    override fun getItemCount(): Int = todolist!!.size

    class MainHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val todoItemName: TextView = itemView.findViewById(R.id.todoItemName)
        val todoCompletedDate: TextView = itemView.findViewById(R.id.todoCompletedDate)

    }

}