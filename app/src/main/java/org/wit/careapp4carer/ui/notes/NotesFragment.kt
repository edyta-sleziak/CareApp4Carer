package org.wit.careapp4carer.ui.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import org.wit.careapp4carer.R

class NotesFragment : Fragment() {

    private lateinit var notesViewModel: NotesViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        notesViewModel =
            ViewModelProviders.of(this).get(NotesViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_notes, container, false)
        val textView: TextView = root.findViewById(R.id.text_notes)
        notesViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}