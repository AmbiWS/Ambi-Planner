package com.ambiws.ambiplanner.core.receiver

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.ambiws.ambiplanner.R

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val title = intent.getStringExtra("EXTRA_TITLE") ?: "Routine Reminder"
        val description = intent.getStringExtra("EXTRA_DESCRIPTION") ?: "Need to be completed."
        val notificationId = intent.getIntExtra("EXTRA_ID", 0)

        showNotification(context, title, description, notificationId)
    }

    private fun showNotification(context: Context, title: String, description: String, notificationId: Int) {
        val channelId = "routine_channel"
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Routine Notifications",
                NotificationManager.IMPORTANCE_HIGH
            )
            channel.description = "Notifications for routine timers"
            channel.enableLights(true)
            channel.enableVibration(true)
            channel.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION), null)
            notificationManager.createNotificationChannel(channel)
        }

        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(title)
            .setContentText(description)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))

        notificationManager.notify(notificationId, builder.build())
    }
}
