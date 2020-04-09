package org.wit.careapp4carer.ui.notifications

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_add_notification, container, false)

        view.button_saveNotification.setOnClickListener {
            val selectedNotification: NotificationsModel?
            if (arguments?.get("notification") == null) {
                var notification = notification_text.text.toString()
                var displayTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                var newNotification = NotificationsModel("",notification, displayTime)
                notificationsList.addNewNotification(newNotification)
                Log.d("D","added + $notification")
                it.findNavController().navigate(R.id.nav_notifications)
            } else {
                var args: AddNotificationFragmentArgs =
                    AddNotificationFragmentArgs.fromBundle(requireArguments())
                selectedNotification = args.notification
                Log.d("D","edited")
                if (selectedNotification != null) {
                    var notification = notification_text.text.toString()
                    var displayTime = LocalDateTime.now()
                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                    var updatedNotification = NotificationsModel(selectedNotification.id, notification, displayTime)
                    notificationsList.editNotification(updatedNotification)
                }
                it.findNavController().navigate(R.id.nav_notifications)
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
            notification_date.setText(passedNotification?.displayTime)
            Log.d("msg", "$passedNotification")
        }

    }

//    // TODO: Rename method, update argument and hook method into UI event
//    fun onButtonPressed(uri: Uri) {
//        listener?.onFragmentInteraction(uri)
//    }


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
