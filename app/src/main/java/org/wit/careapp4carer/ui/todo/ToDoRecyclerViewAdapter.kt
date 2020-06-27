package org.wit.careapp4carer.ui.todo

import android.content.Context
import android.graphics.*
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_todo.view.*
import kotlinx.android.synthetic.main.listitem_todo.view.*
import org.wit.careapp4carer.R
import org.wit.careapp4carer.models.TodoModel
import org.wit.careapp4carer.models.firebase.TodoFireStore

class ToDoRecyclerViewAdapter(var todolist: List<TodoModel>
) : RecyclerView.Adapter<ToDoRecyclerViewAdapter.MainHolder>() {

    private var mContext: Context? = null
    private var dbToDoList = TodoFireStore()

    val swipeToDeleteCallback = object : SwipeToDeleteCallback() {
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val pos = viewHolder.adapterPosition
            dbToDoList.remove(todolist.get(pos).id)
            notifyDataSetChanged()
            notifyItemRemoved(pos)
            notifyItemRangeChanged(pos, todolist.size)

            if (direction == ItemTouchHelper.LEFT) {
                dbToDoList.remove(todolist.get(pos).id)
            }
        }
    }

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
        val mToDoListItem = todolist!!.get(position)

        if(mToDoListItem.task != null) {
            holder.todoItemName.setText(mToDoListItem.task)
        }

        if(mToDoListItem.dateCompleted != null) {
            holder.todoCompletedDate.setText(mToDoListItem.dateCompleted)
        }

        holder.todoItemName.setOnClickListener{
            var action : ToDoFragmentDirections.ActionNavTodoToToDoItemEditFragment = ToDoFragmentDirections.actionNavTodoToToDoItemEditFragment(mToDoListItem)
            holder.itemView.findNavController().navigate(action)
        }


    }

    override fun getItemCount(): Int = todolist!!.size

    class MainHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val todoItemName: TextView = itemView.findViewById(R.id.todoItemName)
        val todoCompletedDate: TextView = itemView.findViewById(R.id.todoCompletedDate)

    }

    abstract class SwipeToDeleteCallback : ItemTouchHelper.Callback() {

        override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
            val swipeFlag = ItemTouchHelper.LEFT
            return makeMovementFlags(0, swipeFlag)
        }

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        override fun onChildDraw(
            c: Canvas,
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            dX: Float,
            dY: Float,
            actionState: Int,
            isCurrentlyActive: Boolean
        ) {
            val icon: Bitmap
            if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {

                val itemView = viewHolder.itemView
                val height = itemView.bottom.toFloat() - itemView.top.toFloat()
                val width = height / 3
                val p = Paint()

                if (dX < 0) {
                    p.color = Color.parseColor("#D32F2F")
                    val background = RectF(
                        itemView.right.toFloat() + dX,
                        itemView.top.toFloat(),
                        itemView.right.toFloat(),
                        itemView.bottom.toFloat()
                    )
                    c.drawRect(background, p)
                    icon = BitmapFactory.decodeResource(itemView.context.resources, R.drawable.delete)
                    val icon_dest = RectF(
                        itemView.right.toFloat() - 2 * width,
                        itemView.top.toFloat() + width,
                        itemView.right.toFloat() - width,
                        itemView.bottom.toFloat() - width
                    )
                    c.drawBitmap(icon, null, icon_dest, p)
                }
                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
            }

        }

    }

}