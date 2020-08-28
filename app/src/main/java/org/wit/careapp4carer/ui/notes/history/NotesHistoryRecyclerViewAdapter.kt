package org.wit.careapp4carer.ui.notes.history

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

class NotesHistoryRecyclerViewAdapter(var notesList: List<NotesModel>
) : RecyclerView.Adapter<NotesHistoryRecyclerViewAdapter.MainHolder>() {

    private var mContext: Context? = null
    private var dbNotesList = NotesFireStore()

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
            val action : NotesHistoryFragmentDirections.ActionNotesHistoryFragmentToNoteDetailsFragment = NotesHistoryFragmentDirections.actionNotesHistoryFragmentToNoteDetailsFragment(mNotesListItem)
            it.findNavController().navigate(action)
        }


    }

    override fun getItemCount(): Int = notesList!!.size

    class MainHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val noteTitle: TextView = itemView.findViewById(R.id.note_title)
        val updateDate: TextView = itemView.findViewById(R.id.note_update_date)
        val updatedBy: TextView = itemView.findViewById(R.id.note_updated_by)

    }

}