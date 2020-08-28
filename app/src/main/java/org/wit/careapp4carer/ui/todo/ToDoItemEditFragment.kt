package org.wit.careapp4carer.ui.todo

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
import android.widget.Toast
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_to_do_item_edit.*
import kotlinx.android.synthetic.main.fragment_to_do_item_edit.view.*
import org.wit.careapp4carer.R
import org.wit.careapp4carer.models.NotificationsModel
import org.wit.careapp4carer.models.TodoModel
import org.wit.careapp4carer.models.firebase.NotificationsFireStore
import org.wit.careapp4carer.models.firebase.TodoFireStore
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [toDoItemEditFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [toDoItemEditFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class toDoItemEditFragment : Fragment() {

    private var listener: OnFragmentInteractionListener? = null
    private var toDoFirestore = TodoFireStore()
    private var notificationsFirestore = NotificationsFireStore()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_to_do_item_edit, container, false)

        val args: toDoItemEditFragmentArgs =
            toDoItemEditFragmentArgs.fromBundle(requireArguments())
        val selectedToDoItem = args.task



        if(selectedToDoItem?.notificationExists == true) {
            view.notification_date.setVisibility(View.INVISIBLE)
            view.notification_time.setVisibility(View.INVISIBLE)
            view.button_create_notification.setVisibility(View.INVISIBLE)
            view.notification_info.setVisibility(View.VISIBLE)
        }

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

        view.button_save_changes.setOnClickListener {
//            val args: toDoItemEditFragmentArgs =
//                toDoItemEditFragmentArgs.fromBundle(requireArguments())
//            val selectedToDoItem = args.task
            val task = task_title.text.toString()
            if (task == "") {
                val text = "Please fill all fields!"
                val duration = Toast.LENGTH_LONG
                val toast = Toast.makeText(requireContext(), text, duration)
                toast.show()
            } else {
                val updatedTask = TodoModel(selectedToDoItem!!.id, task)
                toDoFirestore.edit(updatedTask)
                it.findNavController().navigate(R.id.nav_todo)
            }
        }


        view.button_create_notification.setOnClickListener {
//            val args: toDoItemEditFragmentArgs =
//                toDoItemEditFragmentArgs.fromBundle(requireArguments())
//            val selectedTask = args.task
            if (selectedToDoItem?.notificationExists == true) {
                val text = "Notification already exists. Change possible from notifications screen!"
                val duration = Toast.LENGTH_LONG
                val toast = Toast.makeText(requireContext(), text, duration)
                toast.show()
            } else {
                val notification = task_title.text.toString()
                val displayDate = notification_date.text.toString()
                val displayTime = notification_time.text.toString()
                if (notification == "" || displayDate == "" || displayTime == "") {
                    val text = "Please fill all fields!"
                    val duration = Toast.LENGTH_LONG
                    val toast = Toast.makeText(requireContext(), text, duration)
                    toast.show()
                } else {
                    val newNotification =
                        NotificationsModel("", notification, displayDate, displayTime)
                    notificationsFirestore.addNewNotification(newNotification)
                    val updatedTask = TodoModel(selectedToDoItem!!.id, notification, false, "Not completed", notificationExists = true)
                    toDoFirestore.edit(updatedTask)
                    val text = "Notification created!"
                    val duration = Toast.LENGTH_LONG
                    val toast = Toast.makeText(requireContext(), text, duration)
                    toast.show()
                    it.findNavController().navigate(R.id.nav_todo)
                }
            }
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments != null) {
            var args: toDoItemEditFragmentArgs =
                toDoItemEditFragmentArgs.fromBundle(requireArguments())
            var passedData = args.task
            task_title.setText(passedData?.task)
            Log.d("msg", "$passedData")
        }

    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment toDoItemEditFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            toDoItemEditFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
