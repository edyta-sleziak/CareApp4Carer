package org.wit.careapp4carer.ui.reports

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.wit.careapp4carer.R

class ReportsFragment : Fragment() {

    private lateinit var reportsViewModel: ReportsViewModel
    private lateinit var mRecycleView: RecyclerView
    private lateinit var mRecyclerViewAdapter: ReportRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        reportsViewModel =
            ViewModelProviders.of(this).get(ReportsViewModel::class.java)
        val view = inflater.inflate(R.layout.fragment_reports, container, false)

        mRecycleView = view.findViewById(R.id.reports_recyclerView)
        mRecycleView.layoutManager = LinearLayoutManager(
            requireActivity().applicationContext, RecyclerView.VERTICAL,false)

        reportsViewModel.getData()
            .observe(viewLifecycleOwner, Observer { data ->
                mRecycleView.adapter =
                    ReportRecyclerViewAdapter(
                        data
                    )
            })

        return view
    }
}