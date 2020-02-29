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
import org.wit.careapp4carer.adapters.RecyclerViewAdapter
import org.wit.careapp4carer.models.TodoModel
import org.wit.careapp4carer.models.TodoStore
import org.wit.careapp4carer.models.firebase.TodoFireStore

class ToDoFragment : Fragment() {

    private lateinit var toDoViewModel: ToDoViewModel
    private lateinit var mRecycleView: RecyclerView
    private lateinit var mRecyclerViewAdapter: RecyclerViewAdapter

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
            activity!!.applicationContext, RecyclerView.VERTICAL,false)
        mRecycleView.layoutManager = linearLayoutManager


        toDoViewModel.getToDoList().observe(viewLifecycleOwner, Observer{ todolist ->
            mRecycleView.adapter = RecyclerViewAdapter(todolist)
        })

        view.buttonAdd.setOnClickListener { view ->
            var taskName = newToDoItem.text.toString()
            Log.d("123", taskName)
            //toDoViewModel.addToDoItem(taskName)
            var newToDoItem = TodoModel("123",taskName, false, null,0)
            var todoList = TodoFireStore(activity!!.applicationContext)
            todoList.addNewTodoItem(newToDoItem)
        }

        return view
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        todoRecyclerView.apply {
//            layoutManager = LinearLayoutManager(activity!!.applicationContext)
//            adapter = RecyclerViewAdapter(toDoViewModel.getToDoList())
//        }
//    }

}