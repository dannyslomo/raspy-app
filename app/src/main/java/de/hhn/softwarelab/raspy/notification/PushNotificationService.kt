package de.hhn.softwarelab.raspy.notification

import android.Manifest
import android.R
import android.app.*
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.Constants.TAG
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import de.hhn.softwarelab.raspy.ui.settings.SettingUI

/**
 * Service for handling push notifications using Firebase Cloud Messaging.
 */
class PushNotificationService : FirebaseMessagingService() {
    /**
     * Called when a new message is received.
     *
     * @param message The received remote message.
     */
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        val type = message.data["type"]?.toIntOrNull() ?: return
        getNotification(type, applicationContext)
    }
    /**
     * Gets the appropriate notification based on the given type.
     *
     * @param type The type of notification.
     * @param context The application context.
     */
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun getNotification(type: Int, context: Context) {
        when (type) {
            1 -> {
                val title = "Camera DETECTED strange activity"
                val text = "!!! CHECK NOW !!!"
                val icon = R.drawable.ic_dialog_alert
                createPostNotification(context, title, text, icon, CHANNEL_CAMERA_DETECTION)
            }
            2 -> {
                if (!isNetworkConnected(context)) {
                    val title = "RaSpy: No Connection"
                    val text = "Check your network connection"
                    val icon = R.drawable.ic_input_delete
                    createPostNotification(context, title, text, icon, CHANNEL_NETWORK_CONNECTION)
                } else {
                    val title = "RaSpy: Connection"
                    val text = "RaSpy is connected with Network"
                    val icon = R.drawable.ic_lock_idle_charging
                    createPostNotification(context, title, text, icon, CHANNEL_NETWORK_CONNECTION)
                }
            }
            else -> {
                val title = "RaSpy: Camera DETECTED strange activity"
                val text = "!!! CHECK NOW !!!"
                val icon = R.drawable.ic_dialog_alert
                createPostNotification(context, title, text, icon, CHANNEL_CAMERA_DETECTION)
            }
        }
    }
    /**
     * Creates and displays a post notification.
     *
     * @param context The application context.
     * @param postTitle The title of the notification.
     * @param postContent The content of the notification.
     * @param icon The icon resource ID for the notification.
     * @param channelId The ID of the notification channel.
     */
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun createPostNotification(
        context: Context,
        postTitle: String,
        postContent: String,
        icon: Int,
        channelId: String // Pass the channel ID as a parameter
    ) {
        checkNotificationPermission(context)
        val channel = NotificationChannel(
            channelId,
            getChannelName(channelId), // Get the appropriate channel name
            NotificationManager.IMPORTANCE_HIGH
        )
        val intent = Intent(context, SettingUI::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(icon)
            .setContentTitle(postTitle)
            .setContentText(postContent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.createNotificationChannel(channel)
        notificationManager.notify(POST_RQ, builder.build())

        val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
    }
    /**
     * Gets the name of the channel based on the channel ID.
     *
     * @param channelId The ID of the notification channel.
     * @return The name of the channel.
     */
    private fun getChannelName(channelId: String): String {
        return when (channelId) {
            CHANNEL_CAMERA_DETECTION -> "Camera Detection"
            CHANNEL_NETWORK_CONNECTION -> "Network Connection"
            else -> "Default Channel"
        }
    }
    /**
     * Checks if the device has a network connection.
     *
     * @param context The application context.
     * @return `true` if the device has a network connection, `false` otherwise.
     */
    fun isNetworkConnected(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val networkCapabilities =
            connectivityManager.getNetworkCapabilities(network) ?: return false
        return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
    /**
     * Called when a new FCM token is generated.
     *
     * @param token The new FCM token.
     */
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(TAG, "Refreshed token: $token")

        // You can perform any additional logic here if needed
        // However, since you don't need to send the token to a server, you can leave this method empty
    }

    /**
     * Check if User granted Permission for Post Notification
     */
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun checkNotificationPermission(context: Context) {

        //create channel for notification
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel(
            "my_channel_id", "Camera Detection", NotificationManager.IMPORTANCE_DEFAULT
        )
        channel.description = "My notification channel"
        notificationManager.createNotificationChannel(channel)

        // Check if notification permission is granted
        //if not, pop up dialog
        if (!NotificationManagerCompat.from(context).areNotificationsEnabled()) {
            notificationPermissionDialog(
                context
            )
            Toast.makeText(context, "Notification is not granted", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Dialog with positive and negative button, that will pop up if user has not granted permission
     * for notification yet
     * positive -> request notification permission
     * negative -> close Dialog and do nothing
     */
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun notificationPermissionDialog(
        context: Context
    ) {
        val builder = AlertDialog.Builder(context)

        builder.apply {
            setMessage("Message")
            setTitle("Title")
            setPositiveButton("Yes, OK") { _, _ ->
                ActivityCompat.requestPermissions(
                    context as Activity,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    POST_RQ
                )
            }
            setNegativeButton("No") { dialog, _ ->
                // remind user with toast that they don´t receive notifications
                Toast.makeText(context, "Okay, No Notifications!", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
            val dialog = builder.create()
            dialog.show()
        }
    }

    /**
     * allows you to subscribe to a specific topic to receive push notifications.
     */
    companion object {
        private const val POST_RQ = 101

        // Define channel IDs for different notification types
        private const val CHANNEL_CAMERA_DETECTION = "channel_camera_detection"
        private const val CHANNEL_NETWORK_CONNECTION = "channel_network_connection"
        fun subscribePushNotifications(topic: String, context: Context) {
            FirebaseMessaging.getInstance().subscribeToTopic(topic)
                .addOnCompleteListener { task ->
                    var msg = "Subscribed to Notifications: $topic"
                    if (!task.isSuccessful) {
                        msg = "Subscribe failed"
                    }
                    Log.d(TAG, msg)
                }
        }
    }
}