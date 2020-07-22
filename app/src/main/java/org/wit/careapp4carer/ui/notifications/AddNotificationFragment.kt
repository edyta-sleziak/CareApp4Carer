package org.wit.careapp4carer.ui.notifications

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_add_notification.*
import kotlinx.android.synthetic.main.fragment_add_notification.view.*
import kotlinx.android.synthetic.main.listitem_notification.*
//import org.wit.careapp4carer.AddNotificationFragmentArgs
import org.wit.careapp4carer.R
import org.wit.careapp4carer.models.NotificationsModel
import org.wit.careapp4carer.models.firebase.NotificationsFireStore
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.math.min

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [AddNotificationFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [AddNotificationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddNotificationFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null
    private var notificationsList = NotificationsFireStore()
    private lateinit var notificationViewModel: NotificationsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_add_notification, container, false)

        view.notification_date.setOnClickListener {
            val datepicker = Calendar.getInstance()
            val year = datepicker.get(Calendar.YEAR)
            val month = datepicker.get(Calendar.MONTH)
            val day = datepicker.get(Calendar.DAY_OF_MONTH )
            val datePickerDialog = DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener { DatePicker, year, month, dayOfMonth ->
                notification_date.setText("$dayOfMonth/"+ ((month)+1) +"/$year")
            }, year, month, day)
            datePickerDialog.show()
        }

        view.notification_time.setOnClickListener {
            val timepicker = Calendar.getInstance()
            val hour = timepicker.get(Calendar.HOUR)
            val minutes = timepicker.get(Calendar.MINUTE)
            val timePickerDialog = TimePickerDialog(requireContext(), TimePickerDialog.OnTimeSetListener { TimePicker, hour, minutes ->
                notification_time.setText("$hour:$minutes")
            }, hour, minutes, true)
            timePickerDialog.show()
        }

        view.button_saveNotification.setOnClickListener {
            val args: AddNotificationFragmentArgs =
                AddNotificationFragmentArgs.fromBundle(requireArguments())
            val selectedNotification = args.notification
            if (arguments?.get("notification") == null || selectedNotification?.id == "") {
                val notification = notification_text.text.toString()
                val displayDate = notification_date.text.toString()
                val displayTime = notification_time.text.toString()
                if (notification == "" || displayDate == "" || displayTime == "") {
                    val text = "Please fill all fields!"
                    val duration = Toast.LENGTH_LONG
                    val toast = Toast.makeText(requireContext(), text, duration)
                    toast.show()
                } else {
                    val newNotification = NotificationsModel("", notification, displayDate, displayTime)
                    notificationsList.addNewNotification(newNotification)
                    it.findNavController().navigate(R.id.nav_notifications)
                }
            } else {
                if (selectedNotification != null) {
                    val notification = notification_text.text.toString()
                    val displayDate = notification_date.text.toString()
                    val displayTime = notification_time.text.toString()
                    if (notification == "" || displayDate == "" || displayTime == "") {
                        val text = "Please fill all fields!"
                        val duration = Toast.LENGTH_LONG
                        val toast = Toast.makeText(requireContext(), text, duration)
                        toast.show()
                    } else {
                        val updatedNotification = NotificationsModel(selectedNotification.id, notification, displayDate, displayTime)
                        notificationsList.editNotification(updatedNotification)
                        it.findNavController().navigate(R.id.nav_notifications)
                    }
                }
            }
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments != null) {
            var args: AddNotificationFragmentArgs =
                AddNotificationFragmentArgs.fromBundle(requireArguments())
            var passedNotification = args.notification
            notification_text.setText(passedNotification?.notification)
            notification_date.setText(passedNotification?.displayDate)
            notification_time.setText(passedNotification?.displayTime)
            Log.d("msg", "$passedNotification")
        }

    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        @JvmStatic
        fun newInstance() = AddNotificationFragment()
    }
}
