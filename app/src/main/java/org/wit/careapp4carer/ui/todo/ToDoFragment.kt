package org.wit.careapp4carer.ui.todo

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_todo.*
import kotlinx.android.synthetic.main.fragment_todo.view.*
import kotlinx.android.synthetic.main.listitem_todo.view.*
import org.wit.careapp4carer.R
import org.wit.careapp4carer.ui.todo.ToDoRecyclerViewAdapter
import org.wit.careapp4carer.models.TodoModel
import org.wit.careapp4carer.models.TodoStore
import org.wit.careapp4carer.models.firebase.TodoFireStore

class ToDoFragment : Fragment() {

    private lateinit var toDoViewModel: ToDoViewModel
    private lateinit var mRecycleView: RecyclerView
    private lateinit var mRecyclerViewAdapter: ToDoRecyclerViewAdapter
    var todoList = TodoFireStore()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        toDoViewModel =
            ViewModelProviders.of(this).get(ToDoViewModel::class.java)
        val view = inflater.inflate(R.layout.fragment_todo, container, false)

        mRecycleView = view.findViewById(R.id.todoRecyclerView)
        val linearLayoutManager = LinearLayoutManager(
            requireActivity().applicationContext, RecyclerView.VERTICAL,false)
        mRecycleView.layoutManager = linearLayoutManager

        toDoViewModel.getToDoList()
            .observe(viewLifecycleOwner, Observer{ todolist ->
            mRecycleView.adapter = ToDoRecyclerViewAdapter(todolist)
        })

        view.buttonAdd.setOnClickListener { view ->
            var taskName = newToDoItem.text.toString()
            var newToDoItem = TodoModel("",taskName, false, null)
            todoList.addNewTodoItem(newToDoItem)
        }

        return view
    }

}