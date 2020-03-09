package org.wit.careapp4carer.ui.notifications.addEditNotification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import org.wit.careapp4carer.R
import org.wit.careapp4carer.models.firebase.NotificationsFireStore
import org.wit.careapp4carer.ui.notifications.NotificationsRecyclerViewAdapter
import org.wit.careapp4carer.ui.notifications.NotificationsViewModel


class AddEditNotificationFragment : Fragment() {

    private lateinit var addEditNotificationViewModel: AddEditNotificationViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        addEditNotificationViewModel =
            ViewModelProviders.of(this).get(AddEditNotificationViewModel::class.java)
        val view = inflater.inflate(R.layout.single_notification, container, false)


        return view
    }
}