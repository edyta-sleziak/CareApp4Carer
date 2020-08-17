package org.wit.careapp4carer.ui.todo.history

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import org.jetbrains.anko.internals.AnkoInternals.getContext
import org.wit.careapp4carer.R
import org.wit.careapp4carer.models.NotesModel
import org.wit.careapp4carer.models.TodoModel
import org.wit.careapp4carer.models.firebase.NotesFireStore
import org.wit.careapp4carer.models.firebase.TodoFireStore
import org.wit.careapp4carer.ui.notes.NotesFragmentDirections

class ToDoHistoryRecyclerViewAdapter(var todoList: List<TodoModel>
) : RecyclerView.Adapter<ToDoHistoryRecyclerViewAdapter.MainHolder>() {

    private var mContext: Context? = null
    private var dbToDoList = TodoFireStore()

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
        val mToDoListItem = todoList.get(position)

        if(mToDoListItem.task != null) {
            holder.task.setText(mToDoListItem.task)
        }

        if(mToDoListItem.dateCompleted != null) {
            holder.completedDate.setText(mToDoListItem.dateCompleted)
        }
    }

    override fun getItemCount(): Int = todoList.size

    class MainHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val task: TextView = itemView.findViewById(R.id.todoItemName)
        val completedDate: TextView = itemView.findViewById(R.id.todoCompletedDate)

    }

}