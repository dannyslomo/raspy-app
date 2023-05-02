package de.hhn.softwarelab.raspy.notification

import android.Manifest
import android.app.*
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.OnRequestPermissionsResultCallback
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import de.hhn.softwarelab.raspspy.R


class NotificationUtils : AppCompatActivity(), OnRequestPermissionsResultCallback {
    private var POST_RQ = 101

    /**
     * get post notification
     * type 1 = camera detection
     * type 2 = no network connection
     * (type 3 = someone familiar got detected
     */
    fun getNotification(type: Int, context: Context) {
        when {
            //camera detected something strange notification
            type == 1 -> createPostNotification(
                context,
                "Camera DETECTED strange activity",
                "!!! CHECK NOW !!!",
                R.drawable.notification_icon
            )
            //no network connection notification
            type == 2 ->
                if (!isNetworkConnected(context)) {
                    createPostNotification(
                        context,
                        "RaSpy: No Connection",
                        "check your network connection",
                        R.drawable.network_icon
                    )
                }
            else -> createPostNotification(
                context,
                "Camera DETECTED strange activity",
                "!!! CHECK NOW !!!",
                R.drawable.notification_icon
            )
        }
    }

    /**
     * creating a post notification that will show on notification bar and lockscreen
     */
    private fun createPostNotification(
        context: Context,
        postTitle: String,
        postContent: String,
        icon: Int
    ) {
        val channelId = "post_notification_channel"

        // Create a notification builder
        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(icon)
            .setContentTitle(postTitle)
            .setContentText(postContent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        // Create a notification manager
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Check if the Android version is Oreo or higher, and create a notification channel if necessary
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Post Notification",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        // Show the notification
        notificationManager.notify(POST_RQ, builder.build())
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
            NotificationPermissionDialog(
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
    private fun NotificationPermissionDialog(
        context: Context
    ) {
        val builder = AlertDialog.Builder(context)

        builder.apply {
            setMessage(R.string.permission_message)
            setTitle(R.string.permission_title)
            setPositiveButton(R.string.notification_Dialog_ButtonPos) { _, _ ->
                ActivityCompat.requestPermissions(
                    context as Activity,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    POST_RQ
                )
            }
            setNegativeButton(R.string.notification_Dialog_ButtonNeg) { dialog, _ ->
                // remind user with toast that they don´t receive notifications
                Toast.makeText(context, "Okay, No Notifications!", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
            val dialog = builder.create()
            dialog.show()
        }
    }

    /**
     * Check if device is connected to Network because
     * @return Boolean
     */
    private fun isNetworkConnected(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //connected
            val network = connectivityManager.activeNetwork ?: return false
            val networkCapabilities =
                connectivityManager.getNetworkCapabilities(network) ?: return false
            return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        } else {
            //not connected
            val networkInfo = connectivityManager.activeNetworkInfo ?: return false
            return networkInfo.isConnected
        }
    }
}


