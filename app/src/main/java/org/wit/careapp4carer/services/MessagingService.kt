package org.wit.careapp4carer.services

import android.content.Intent
import android.util.Log
import androidx.core.app.ActivityCompat.startActivityForResult
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.jetbrains.anko.intentFor
import org.wit.careapp4carer.models.NotificationsModel
import org.wit.careapp4carer.ui.notifications.NotificationsFragment
import android.os.Bundle
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import org.wit.careapp4carer.models.firebase.AccountInfoFireStore


class MessagingService : FirebaseMessagingService() {

//    val accountFirestore = AccountInfoFireStore()

//    override fun onNewToken(token: String?) {
//        Log.d("Tag",token)
//        if(token != null) {
//            accountFirestore.addToken(token)
//        }
//        super.onNewToken(token)
//    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        if(remoteMessage.notification != null) {
            Log.d("SOS notification", remoteMessage.data.toString())
        } else {
            Log.d("FCM", "Empty message received!")
        }
    }

}