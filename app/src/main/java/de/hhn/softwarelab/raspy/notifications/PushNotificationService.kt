package de.hhn.softwarelab.raspy.notifications

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.firebase.messaging.Constants.TAG
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class PushNotificationService: FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

    }

    companion object{
        fun subscribePushNotifications(topic: String, context: Context){
            FirebaseMessaging.getInstance().subscribeToTopic(topic)
                .addOnCompleteListener { task ->
                    var msg = "Subscribed to Notifications: $topic"
                    if (!task.isSuccessful) {
                        msg = "Subscribe failed"
                    }
                    Log.d(TAG, msg)
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                }
        }
    }
}