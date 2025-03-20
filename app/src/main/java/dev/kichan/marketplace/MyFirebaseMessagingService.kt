package dev.kichan.marketplace

import android.Manifest
import android.R
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)

        Log.d("FCM", "Refreshed token: $token")
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        val notificationManager = NotificationManagerCompat.from(
            applicationContext
        )

        var builder: NotificationCompat.Builder? = null
        val CHANNEL_ID = "id"
        if (notificationManager.getNotificationChannel(CHANNEL_ID) == null) {
            val CHANNEL_NAME = "asdasd"
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            )
            channel.enableLights(true)
            channel.enableVibration(true)
            notificationManager.createNotificationChannel(channel)
        }
        builder = NotificationCompat.Builder(applicationContext, CHANNEL_ID)

        val title = remoteMessage.notification!!.title
        val body = remoteMessage.notification!!.body

        builder.setContentTitle(title)
            .setContentText(body)
            .setSmallIcon(R.drawable.star_on)

        val notification = builder.build()
        val checkSelfPermission =
            ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)

        if (checkSelfPermission != PackageManager.PERMISSION_GRANTED) {
            Log.e("FCM", "권한이 없습니다.")
            return
        }
        notificationManager.notify(1, notification)
        Log.i("FCM", "onMessageReceived: $title, $body")

//        remoteMessage.notification?.apply {
//            val intent = Intent(this@, MainActivity::class.java).apply{
//                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//            }
//            val pendingIntent = PendingIntent.getActivity(this@MyFirebaseMessageService, 0, intent, PendingIntent.FLAG_IMMUTABLE)
//            val builder = NotificationCompat.Builder(this@MyFirebaseMessageService, MainActivity.channel_id)
//                .setSmallIcon(android.R.drawable.ic_dialog_info)
//                .setContentTitle(title)
//                .setContentText(body)
//                .setContentIntent(pendingIntent)
//                .setAutoCancel(true)
//
//            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
//            notificationManager.notify(101, builder.build())
//        }
    }
}