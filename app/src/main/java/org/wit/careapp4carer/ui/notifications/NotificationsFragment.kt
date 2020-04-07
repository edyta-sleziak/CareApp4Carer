package org.wit.careapp4carer.ui.notifications

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.listitem_notification.view.*
import kotlinx.android.synthetic.main.notification_list.view.*
import org.wit.careapp4carer.R
import org.wit.careapp4carer.models.firebase.NotificationsFireStore


class NotificationsFragment : Fragment() {

    private lateinit var notificationViewModel: NotificationsViewModel
    private lateinit var mRecycleView: RecyclerView
    private lateinit var mRecyclerViewAdapter: NotificationsRecyclerViewAdapter
    private var notificationsList = NotificationsFireStore()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {

        notificationViewModel =
            ViewModelProviders.of(this).get(NotificationsViewModel::class.java)
        val view = inflater.inflate(R.layout.fragment_notifications, container, false)

        mRecycleView = view.findViewById(R.id.notification_recyclerView)
        val linearLayoutManager = LinearLayoutManager(
            requireActivity().applicationContext, RecyclerView.VERTICAL,false)
        mRecycleView.layoutManager = linearLayoutManager

        notificationViewModel.getNotificationsList()
            .observe(viewLifecycleOwner, Observer{ notificationslist ->
                mRecycleView.adapter =
                    NotificationsRecyclerViewAdapter(
                        notificationslist
                    )
            })

        view.button_addNotification.setOnClickListener {
            view.findNavController().navigate(R.id.addNotificationFragment)
        }

        view.button_seeHistory.setOnClickListener {
            view.findNavController().navigate(R.id.notificationHistoryFragment)
        }

        return view
    }

}