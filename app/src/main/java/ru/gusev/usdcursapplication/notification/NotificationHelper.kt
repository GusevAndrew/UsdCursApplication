package ru.gusev.usdcursapplication.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import ru.gusev.usdcursapplication.R
import ru.gusev.usdcursapplication.ui.main.MainActivity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationHelper @Inject constructor(private val context: Context) {

    companion object {
        private const val DEFAULT_NOTIFICATION_ID = 0
    }

    private val channelId by lazy {
        context.getString(R.string.default_notification_channel_id)
    }

    fun sendNotification(bodyMessage: String) {
        val notificationManager =
            getNotificationManagerWithChannel(context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
        val notificationBuilder = getNotificationBuilder(bodyMessage)

        notificationManager.notify(DEFAULT_NOTIFICATION_ID, notificationBuilder.build())
    }

    private fun getNotificationManagerWithChannel(notificationManager: NotificationManager): NotificationManager {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(
                NotificationChannel(
                    channelId,
                    context.getString(R.string.notification_visible_channel_name),
                    NotificationManager.IMPORTANCE_DEFAULT
                )
            )
        }

        return notificationManager
    }

    private fun getNotificationBuilder(bodyMessage: String): NotificationCompat.Builder {
        return NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_usd_24dp)
            .setContentTitle(context.getString(R.string.app_name))
            .setContentText(bodyMessage)
            .setContentIntent(
                getClickNotificationIntent()
            )
            .setAutoCancel(true)
    }

    private fun getClickNotificationIntent(): PendingIntent {
        val mainActivityIntent = Intent(context, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }

        return PendingIntent.getActivity(
            context,
            0,
            mainActivityIntent,
            PendingIntent.FLAG_ONE_SHOT
        )
    }
}