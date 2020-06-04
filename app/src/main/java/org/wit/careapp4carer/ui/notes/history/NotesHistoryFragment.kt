package org.wit.careapp4carer.ui.notes.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.wit.careapp4carer.R
import org.wit.careapp4carer.ui.notes.NotesRecyclerViewAdapter
import org.wit.careapp4carer.ui.notes.NotesViewModel


class NotesHistoryFragment : Fragment() {
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
        val view = inflater.inflate(R.layout.fragment_notes_history, container, false)

        mRecycleView = view.findViewById(R.id.notesHistory_recyclerView)
        mRecycleView.layoutManager = LinearLayoutManager(
            requireActivity().applicationContext, RecyclerView.VERTICAL,false)


        notesViewModel.getRemovedNotesList()
            .observe(viewLifecycleOwner, Observer{ noteslist ->
                mRecycleView.adapter =
                    NotesHistoryRecyclerViewAdapter(
                        noteslist
                    )
            })

        return view
    }
}