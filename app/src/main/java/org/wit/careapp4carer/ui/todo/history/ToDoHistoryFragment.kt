package org.wit.careapp4carer.ui.todo.history

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.wit.careapp4carer.R
import org.wit.careapp4carer.ui.todo.ToDoViewModel


class toDoHistoryFragment : Fragment() {
    private lateinit var toDoViewModel: ToDoViewModel
    private lateinit var mRecycleView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        toDoViewModel =
            ViewModelProviders.of(this).get(ToDoViewModel::class.java)
        val view = inflater.inflate(R.layout.fragment_to_do_history, container, false)

        mRecycleView = view.findViewById(R.id.todoHistoryRecyclerView)
        mRecycleView.layoutManager = LinearLayoutManager(
            requireActivity().applicationContext, RecyclerView.VERTICAL,false)


        toDoViewModel.getHistory()
            .observe(viewLifecycleOwner, Observer{ todolist ->
                mRecycleView.adapter =
                    ToDoHistoryRecyclerViewAdapter(
                        todolist
                    )
            })

        return view
    }

    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

}
