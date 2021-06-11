package net.eucalypto.bignerdranch.photogallery.backgroundpoll

import android.app.Activity
import android.app.Notification
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationManagerCompat
import timber.log.Timber

class NotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Timber.d("received broadcast: ${intent.action}, result: $resultCode")

        if (resultCode != Activity.RESULT_OK) {
            Timber.d("Not showing message because broadcast intent was canceled by another receiver")
            return
        }

        val requestCode = intent.getIntExtra(PUT_EXTRA_REQUEST_CODE, 0)
        val notification: Notification =
            intent.getParcelableExtra(PUT_EXTRA_NOTIFICATION)!!

        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(requestCode, notification)
    }

    companion object {
        const val PUT_EXTRA_REQUEST_CODE = "put_extra_request_code"
        const val PUT_EXTRA_NOTIFICATION = "put_extra_notification"
        const val ACTION_SHOW_NOTIFICATION =
            "net.eucalypto.bignerdranch.photogallery.SHOW_NOTIFICATION"

        fun createIntent(requestCode: Int, notification: Notification): Intent {
            return Intent(ACTION_SHOW_NOTIFICATION).apply {
                putExtra(PUT_EXTRA_REQUEST_CODE, requestCode)
                putExtra(PUT_EXTRA_NOTIFICATION, notification)
            }
        }
    }
}