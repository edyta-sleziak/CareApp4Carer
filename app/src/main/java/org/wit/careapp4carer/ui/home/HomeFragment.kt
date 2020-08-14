package org.wit.careapp4carer.ui.home

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.notification_list.view.*
import org.wit.careapp4carer.R
import org.wit.careapp4carer.models.firebase.NotesFireStore
import org.wit.careapp4carer.models.firebase.NotificationsFireStore
import org.wit.careapp4carer.models.firebase.TodoFireStore

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

        val viewModel = HomeViewModel()

        viewModel.getActiveNotificationCount()
            .observe(viewLifecycleOwner, Observer { data ->
                val activeNotificationsButton: TextView = view.findViewById(R.id.button_active_notifications)
                activeNotificationsButton.setText("$data active notifications")
            })

        viewModel.getCompletedNotificationCount()
            .observe(viewLifecycleOwner, Observer { data ->
                val activeNotificationsButton: TextView = view.findViewById(R.id.button_completed_notifications)
                activeNotificationsButton.setText("$data completed notifications")
            })

        viewModel.getNotesCount()
            .observe(viewLifecycleOwner, Observer { data ->
                val sharedNotesCountButton: TextView = view.findViewById(R.id.button_shared_notes)
                sharedNotesCountButton.setText("$data shared notes")
            })

        viewModel.getToDoItemsCount()
            .observe(viewLifecycleOwner, Observer { data ->
                val sharedNotesCountButton: TextView = view.findViewById(R.id.button_active_todo)
                sharedNotesCountButton.setText("$data active to-do items")
            })

        viewModel.getLatestLocation()
            .observe(viewLifecycleOwner, Observer { data ->
                val locationButton: TextView = view.findViewById(R.id.button_current_location)
                //todo compare latest location with home location
                val locationText = ""
                val homeLocation = ""
                val epName = "Patient" //todo get name
                if (locationText == homeLocation) {
                    locationButton.setText(epName + " is HOME")
                    locationButton.setBackgroundColor(Color.GREEN)
                } else {
                    locationButton.setText(epName + " is NOT HOME")
                    locationButton.setBackgroundColor(Color.RED)
                }
            })

        viewModel.getLatestHrReading()
            .observe(viewLifecycleOwner, Observer { data ->
                val locationButton: TextView = view.findViewById(R.id.button_hr)
                //todo compare latest hr with expected
                val latestHr = data.hrValue
                val upperLimit = 100
                val lowerLimit = 55
                if (latestHr in lowerLimit..upperLimit) {
                    locationButton.setText("Latest HR is in safe range ( $latestHr bpm)")
                    locationButton.setBackgroundColor(Color.GREEN)
                } else {
                    locationButton.setText("Latest HR is NOT in safe range ( $latestHr bpm)")
                    locationButton.setBackgroundColor(Color.RED)
                }
            })



        view.button_active_notifications.setOnClickListener {
            view.findNavController().navigate(R.id.nav_notifications)
        }

        view.button_completed_notifications.setOnClickListener {
            view.findNavController().navigate(R.id.notificationHistoryFragment)
        }

        view.button_shared_notes.setOnClickListener {
            view.findNavController().navigate(R.id.nav_notes)
        }

        view.button_active_todo.setOnClickListener {
            view.findNavController().navigate(R.id.nav_todo)
        }

        view.button_current_location.setOnClickListener {
            //todo add new view with location history - click to see map
        }

        view.button_hr.setOnClickListener {
            //todo add hr history view
        }

        return view
    }
}