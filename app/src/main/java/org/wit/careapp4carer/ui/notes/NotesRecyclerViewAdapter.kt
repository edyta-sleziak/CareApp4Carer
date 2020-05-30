package org.wit.careapp4carer.ui.notes

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
import org.wit.careapp4carer.models.firebase.NotesFireStore
import org.wit.careapp4carer.ui.notes.NotesFragmentDirections

class NotesRecyclerViewAdapter(var notesList: List<NotesModel>
) : RecyclerView.Adapter<NotesRecyclerViewAdapter.MainHolder>() {

    private var mContext: Context? = null
    private var dbNotesList = NotesFireStore()

    val swipeToDeleteCallback = object : SwipeToDeleteCallback() {
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val pos = viewHolder.adapterPosition
            dbNotesList.remove(notesList.get(pos).id)
            notifyDataSetChanged()
            notifyItemRemoved(pos)
            notifyItemRangeChanged(pos, notesList.size)

            if (direction == ItemTouchHelper.LEFT) {
                dbNotesList.remove(notesList.get(pos).id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        this.mContext=parent.context
        return MainHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.listitem_notes,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val mNotesListItem = notesList!!.get(position)

        if(mNotesListItem.title != null) {
            holder.noteTitle.setText(mNotesListItem.title)
        }

        if(mNotesListItem.updatedDate != null) {
            holder.updateDate.setText(mNotesListItem.updatedDate)
        }

        if(mNotesListItem.updatedBy != null) {
            holder.updatedBy.setText(mNotesListItem.updatedBy)
        }

        holder.itemView.setOnClickListener{
            Log.d("CLICKED:", " clicked on item ${mNotesListItem?.note}")
            val action : NotesFragmentDirections.ActionNavNotesToAddNoteFragment = NotesFragmentDirections.actionNavNotesToAddNoteFragment(mNotesListItem)
            it.findNavController().navigate(action)
        }


    }

    override fun getItemCount(): Int = notesList!!.size

    class MainHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val noteTitle: TextView = itemView.findViewById(R.id.note_title)
        val updateDate: TextView = itemView.findViewById(R.id.note_update_date)
        val updatedBy: TextView = itemView.findViewById(R.id.note_updated_by)

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