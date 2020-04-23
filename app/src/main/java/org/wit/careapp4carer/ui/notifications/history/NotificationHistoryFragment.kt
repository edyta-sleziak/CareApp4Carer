package org.wit.careapp4carer.ui.notifications.history

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.wit.careapp4carer.R
import org.wit.careapp4carer.models.NotificationsModel
import org.wit.careapp4carer.ui.notifications.NotificationsRecyclerViewAdapter
import org.wit.careapp4carer.ui.notifications.NotificationsViewModel


class NotificationHistoryFragment : Fragment() {

    private lateinit var notificationViewModel: NotificationsViewModel
    private lateinit var mRecycleView: RecyclerView
    private lateinit var mRecyclerViewAdapter: NotificationsRecyclerViewAdapter
    private lateinit var notificationsList : List<NotificationsModel>
    private var listener: OnFragmentInteractionListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {

        notificationViewModel =
            ViewModelProviders.of(this).get(NotificationsViewModel::class.java)
        val view = inflater.inflate(R.layout.fragment_notification_history, container, false)

        mRecycleView = view.findViewById(R.id.notification_history_recyclerView)
        mRecycleView.layoutManager = LinearLayoutManager(
            requireActivity().applicationContext, RecyclerView.VERTICAL,false)


        notificationViewModel.getCompletedNotificationsList()
            .observe(viewLifecycleOwner, Observer{ notificationslist ->
                mRecycleView.adapter =
                    NotificationsRecyclerViewAdapter(
                        notificationslist
                    )
                mRecyclerViewAdapter = NotificationsRecyclerViewAdapter(notificationslist)
                val itemTouchHelper = ItemTouchHelper(mRecyclerViewAdapter.swipeToDeleteCallback)
                itemTouchHelper.attachToRecyclerView(mRecycleView)
            })

        return view
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
        fun newInstance() = NotificationHistoryFragment()
    }

}