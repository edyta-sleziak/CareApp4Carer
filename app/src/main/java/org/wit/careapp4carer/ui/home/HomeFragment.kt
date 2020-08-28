package org.wit.careapp4carer.ui.home

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.notification_list.view.*
import org.wit.careapp4carer.R
import org.wit.careapp4carer.models.AccountInfoModel
import org.wit.careapp4carer.models.LocationModel
import org.wit.careapp4carer.models.firebase.NotesFireStore
import org.wit.careapp4carer.models.firebase.NotificationsFireStore
import org.wit.careapp4carer.models.firebase.TodoFireStore

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var accountData = AccountInfoModel()
    var name = "Patient"

        override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val viewModel = HomeViewModel()

        viewModel.getAccountData()
            .observe(viewLifecycleOwner, Observer { data ->
                accountData = data
                name = accountData.epName
            })

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
                val latestLocation = viewModel.getLatestLocation()
                val homeLocation = LocationModel(accountData.location.latitude, accountData.location.longitude, accountData.location.zoom)
                if (latestLocation.value == LocationModel(0.0,0.0,5f,"")) {
                    locationButton.setText("Location history\nis empty")
                    locationButton.setTextColor(Color.parseColor("#6d6875"))
                } else {
                    if ("%.3f".format(latestLocation.value!!.latitude).toDouble() == ("%.3f".format(
                            homeLocation.latitude
                        ).toDouble()) &&
                        "%.3f".format(latestLocation.value!!.longitude).toDouble() == "%.3f".format(
                            homeLocation.longitude
                        ).toDouble()
                    ) {
                        locationButton.setText(name + "'s\nlocation is\nHOME")
                        locationButton.setTextColor(Color.parseColor("#6d6875"))
                    } else {
                        locationButton.setText(name + "'s\nlocation is\nNOT HOME")
                        locationButton.setTextColor(Color.RED)
                    }
                }
            })

        viewModel.getLatestHrReading()
            .observe(viewLifecycleOwner, Observer { data ->
                val locationButton: TextView = view.findViewById(R.id.button_hr)
                val latestHr = data.hrValue
                val upperLimit = accountData.saveHrRangeHigh.toInt()
                val lowerLimit = accountData.saveHrRangeLow.toInt()
                if (latestHr == 0){
                    locationButton.setText("No heart rate\ndata recorded")
                    locationButton.setTextColor(Color.parseColor("#6d6875"))
                } else {
                    if (latestHr in lowerLimit..upperLimit) {
                        locationButton.setText("Latest HR is \nin safe range \n($latestHr bpm)")
                        locationButton.setTextColor(Color.parseColor("#6d6875"))
                    } else {
                        locationButton.setText("Latest HR is \nNOT in safe range \n($latestHr bpm)")
                        locationButton.setTextColor(Color.RED)
                    }
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
            val latestLocation = viewModel.getLatestLocation()
            val location = LocationModel(latestLocation.value!!.latitude, latestLocation.value!!.longitude, latestLocation.value!!.zoom)
            val action : HomeFragmentDirections.ActionNavHomeToMapFragment = HomeFragmentDirections.actionNavHomeToMapFragment(location)
            it.findNavController().navigate(action)
        }

        view.button_hr.setOnClickListener {
            view.findNavController().navigate(R.id.nav_reports)
        }

        return view
    }
}