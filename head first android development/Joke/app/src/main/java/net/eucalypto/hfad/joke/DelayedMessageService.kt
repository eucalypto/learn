package net.eucalypto.hfad.joke

import android.app.IntentService
import android.app.PendingIntent
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat


const val EXTRA_MESSAGE = "message"
const val NOTIFICATION_ID = 13742

/**
 * An [IntentService] subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 */
class DelayedMessageService : IntentService("DelayedMessageService") {

    override fun onHandleIntent(intent: Intent?) {
        synchronized(this) {
            try {
                Thread.sleep(10000)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
        val text = intent?.getStringExtra(EXTRA_MESSAGE) ?: ""
        showText(text)
    }

    private fun showText(text: String) {
        Log.v("DelayedMessageService", "The message is: $text")
        val builder = NotificationCompat.Builder(applicationContext)
            .setSmallIcon(android.R.drawable.sym_def_app_icon)
            .setContentTitle(getString(R.string.question))
            .setContentText(text)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setVibrate(longArrayOf(0, 1000))
            .setAutoCancel(true)

        val actionIntent = Intent(this, MainActivity::class.java)
        val pending = PendingIntent.getActivity(
            this,
            0,
            actionIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        builder.setContentIntent(pending)


        val notificationManager = NotificationManagerCompat.from(this)

        notificationManager.notify(NOTIFICATION_ID, builder.build())
    }

}