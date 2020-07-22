package org.wit.careapp4carer.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.fragment_settings.*
import kotlinx.android.synthetic.main.fragment_settings.view.*
import org.wit.careapp4carer.R
import org.wit.careapp4carer.models.Location
import org.wit.careapp4carer.ui.notifications.NotificationsFragmentDirections

class SettingsFragment : Fragment() {

    private lateinit var settingsViewModel: SettingsViewModel
    private val viewModel = SettingsViewModel()
    var locationLat = 0.0
    var locationLng = 0.0
    var locationZoom = 6f

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        settingsViewModel =
            ViewModelProviders.of(this).get(SettingsViewModel::class.java)
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        val stringsHrLow = resources.getStringArray(R.array.HrLow)
        val stringsHrHigh = resources.getStringArray(R.array.HrHigh)
        val stringsDistance = resources.getStringArray(R.array.Range)
        val stringsSteps = resources.getStringArray(R.array.Steps)
        val stringsResponseTime = resources.getStringArray(R.array.Time)

        val spinnerHrLow = view.findViewById<Spinner>(R.id.hr_low)
        val spinnerHrHigh = view.findViewById<Spinner>(R.id.hr_high)
        val spinnerDistance = view.findViewById<Spinner>(R.id.home_distance)
        val spinnerSteps = view.findViewById<Spinner>(R.id.steps_goal)
        val spinnerTime = view.findViewById<Spinner>(R.id.response_time)


        //if (spinnerHrLow != null) {
        val adapterHrLow = ArrayAdapter(
            requireActivity(),
            android.R.layout.simple_spinner_item, stringsHrLow
        )
        spinnerHrLow.adapter = adapterHrLow

        val adapterHrHigh = ArrayAdapter(
            requireActivity(),
            android.R.layout.simple_spinner_item, stringsHrHigh
        )
        spinnerHrHigh.adapter = adapterHrHigh

        val adapterRange = ArrayAdapter(
            requireActivity(),
            android.R.layout.simple_spinner_item, stringsDistance
        )
        spinnerDistance.adapter = adapterRange

        val adapterSteps = ArrayAdapter(
            requireActivity(),
            android.R.layout.simple_spinner_item, stringsSteps
        )
        spinnerSteps.adapter = adapterSteps

        val adapterTime = ArrayAdapter(
            requireActivity(),
            android.R.layout.simple_spinner_item, stringsResponseTime
        )
        spinnerTime.adapter = adapterTime

        val data = viewModel.getDataFromDb()
            .observe(viewLifecycleOwner, Observer { accountData ->
                locationLat = accountData.location.lat
                locationLng = accountData.location.lng
                locationZoom = accountData.location.zoom
                view.account_name.setText(accountData.email)
                view.carer_name.setText(accountData.carerName)
                view.patient_name.setText(accountData.epName)
                view.sos_number.setText(accountData.sosContactNumber)
                view.hr_low.setSelection(adapterHrLow.getPosition(accountData.saveHrRangeLow))
                view.hr_high.setSelection(adapterHrHigh.getPosition(accountData.saveHrRangeHigh))
                view.home_distance.setSelection(adapterRange.getPosition(accountData.saveHomeDistance))
                view.steps_goal.setSelection(adapterSteps.getPosition(accountData.dailyStepsGoal))
                view.response_time.setSelection(adapterTime.getPosition(accountData.notificationResponseTime))
                view.home_location.setText("Lat.: "+accountData.location.lat.toString()+"\n Long.: "+accountData.location.lng.toString() )
            })

        view.button_update_account_details.setOnClickListener {
            val accountName = account_name.text.toString()
            val carerName = carer_name.text.toString()
            val patientName = patient_name.text.toString()
            if (accountName == "" || carerName == "" || patientName == "") {
                Toast.makeText(requireContext(), "Please fill all fields!", Toast.LENGTH_LONG)
                    .show()
            } else {
                viewModel.updateAccountInfo(accountName, carerName, patientName)
            }
        }

        view.button_update_password.setOnClickListener {
            val oldPassword = old_password.text.toString()
            val newPassword = new_password.text.toString()

            if (oldPassword == "" || newPassword == "") {
                Toast.makeText(requireActivity(), "Please provide current and new password!", Toast.LENGTH_LONG ).show()
            } else {
                FirebaseAuth.getInstance().currentUser?.updatePassword(newPassword)
                val user = FirebaseAuth.getInstance().currentUser!!
                val email = user.email
                val credentials: AuthCredential = EmailAuthProvider.getCredential(email!!, oldPassword)

                user.reauthenticate(credentials).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            requireActivity(),
                            "Success! Password changed",
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        Toast.makeText(
                            requireActivity(),
                            "Password not changed - old password mismatch",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }

        view.button_save_changes.setOnClickListener {
            val sosContactNumber = sos_number.text.toString()
            val saveHrRangeLow = hr_low.selectedItem.toString()
            val saveHrRangeHigh = hr_high.selectedItem.toString()
            val saveHomeDistance = home_distance.selectedItem.toString()
            val dailyStepsGoal = steps_goal.selectedItem.toString()
            val notificationResponseTime = response_time.selectedItem.toString()
            viewModel.updateAppSettings(
                sosContactNumber,
                saveHrRangeLow,
                saveHrRangeHigh,
                saveHomeDistance,
                dailyStepsGoal,
                notificationResponseTime
            )
        }

        view.home_location.setOnClickListener {
            val location= Location(locationLat,locationLng,locationZoom)
            val action : SettingsFragmentDirections.ActionNavSettingsToMapFragment = SettingsFragmentDirections.actionNavSettingsToMapFragment(location)
            it.findNavController().navigate(action)
        }

        return view
    }
}