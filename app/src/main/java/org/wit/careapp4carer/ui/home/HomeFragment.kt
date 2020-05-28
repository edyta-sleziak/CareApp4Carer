package org.wit.careapp4carer.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.notification_list.view.*
import org.wit.careapp4carer.R
import org.wit.careapp4carer.models.firebase.NotificationsFireStore

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val notificationsFireStore = NotificationsFireStore()

        val numberOfActiveNotifications = notificationsFireStore.getActiveNotificationCount()
        val activeNotificationsCount: TextView = view.findViewById(R.id.button_active_notifications)
        activeNotificationsCount.setText("$numberOfActiveNotifications active notifications")
        val numberOfCompletedNotifications = notificationsFireStore.getCOmpletedNotificationCount()
        val completedNotificationsCount: TextView = view.findViewById(R.id.button_completed_notifications)
        completedNotificationsCount.setText("$numberOfCompletedNotifications completed notifications")

        view.button_active_notifications.setOnClickListener {
            view.findNavController().navigate(R.id.nav_notifications)
        }

        view.button_completed_notifications.setOnClickListener {
            view.findNavController().navigate(R.id.notificationHistoryFragment)
        }

        return view
    }
}