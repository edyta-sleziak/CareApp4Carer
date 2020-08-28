package org.wit.careapp4carer.ui.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_notes.view.*
import org.wit.careapp4carer.R

class NotesFragment : Fragment() {

    private lateinit var notesViewModel: NotesViewModel
    private lateinit var mRecycleView: RecyclerView
    private lateinit var mRecyclerViewAdapter: NotesRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        notesViewModel =
            ViewModelProviders.of(this).get(NotesViewModel::class.java)
        val view = inflater.inflate(R.layout.fragment_notes, container, false)

        mRecycleView = view.findViewById(R.id.notes_recyclerView)
        mRecycleView.layoutManager = LinearLayoutManager(
            requireActivity().applicationContext, RecyclerView.VERTICAL,false)


        notesViewModel.getNotesList()
            .observe(viewLifecycleOwner, Observer{ noteslist ->
                mRecycleView.adapter =
                    NotesRecyclerViewAdapter(
                        noteslist
                    )
                mRecyclerViewAdapter = NotesRecyclerViewAdapter(noteslist)
                val itemTouchHelper = ItemTouchHelper(mRecyclerViewAdapter.swipeToDeleteCallback)
                itemTouchHelper.attachToRecyclerView(mRecycleView)
            })

        view.button_addNote.setOnClickListener {
            var note = null
            var action : NotesFragmentDirections.ActionNavNotesToAddNoteFragment = NotesFragmentDirections.actionNavNotesToAddNoteFragment(note)
            it.findNavController().navigate(action)
        }

        view.button_seeNotesHistory.setOnClickListener {
            view.findNavController().navigate(R.id.notesHistoryFragment)
        }

        return view
    }
}