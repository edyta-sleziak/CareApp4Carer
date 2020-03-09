package org.wit.careapp4carer.ui.notifications

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
import com.google.android.gms.dynamic.SupportFragmentWrapper
import kotlinx.android.synthetic.main.fragment_notifications.view.*
import org.wit.careapp4carer.R
import org.wit.careapp4carer.ui.notifications.addEditNotification.AddEditNotificationFragment


class NotificationsFragment : Fragment() {

    private lateinit var notificationViewModel: NotificationsViewModel
    private lateinit var mRecycleView: RecyclerView
    private lateinit var mRecyclerViewAdapter: NotificationsRecyclerViewAdapter

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
            activity!!.applicationContext, RecyclerView.VERTICAL,false)
        mRecycleView.layoutManager = linearLayoutManager

        notificationViewModel.getNotificationsList()
            .observe(viewLifecycleOwner, Observer{ notificationslist ->
                mRecycleView.adapter = NotificationsRecyclerViewAdapter(notificationslist)
            })



        view.button_addNotification.setOnClickListener { view ->
            val addNotificationFragment = AddEditNotificationFragment()
            val fragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragment_notification, addNotificationFragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        view.button_seeHistory.setOnClickListener { view ->
            //todo
        }

        return view
    }
}