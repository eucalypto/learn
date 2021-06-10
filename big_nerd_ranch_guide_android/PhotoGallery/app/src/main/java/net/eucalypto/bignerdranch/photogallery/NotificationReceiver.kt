package net.eucalypto.bignerdranch.photogallery

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Notification
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationManagerCompat
import timber.log.Timber

class NotificationReceiver : BroadcastReceiver() {
    @SuppressLint("BinaryOperationInTimber")
    override fun onReceive(context: Context, intent: Intent) {
        Timber.d("received broadcast: ${intent.action}, result: $resultCode")

        if (resultCode != Activity.RESULT_OK) {
            val message =
                "Cancelling execution of broadcast intent because it was " +
                        "intercepted and canceled by a dynamic receiver"
            Timber.d(message)
            return
        }

        val requestCode = intent.getIntExtra(PollWorker.REQUEST_CODE, 0)
        val notification: Notification =
            intent.getParcelableExtra(PollWorker.NOTIFICATION)!!

        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(requestCode, notification)
    }
}