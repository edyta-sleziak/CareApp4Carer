package org.wit.careapp4carer.ui.todo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import org.wit.careapp4carer.R

class ToDoFragment : Fragment() {

    private lateinit var toDoViewModel: ToDoViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        toDoViewModel =
            ViewModelProviders.of(this).get(ToDoViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_todo, container, false)
        val textView: TextView = root.findViewById(R.id.text_todo)
        toDoViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}