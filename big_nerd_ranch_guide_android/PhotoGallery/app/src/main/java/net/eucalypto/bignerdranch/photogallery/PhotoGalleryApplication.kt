package net.eucalypto.bignerdranch.photogallery

import android.app.Application
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationManagerCompat
import timber.log.Timber

const val NOTIFICATION_CHANNEL_ID = "flickr_poll"

class PhotoGalleryApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())

        setUpNotificationChannel()
    }

    private fun setUpNotificationChannel() {
        val name = getString(R.string.notification_channel_name)
        val importance = NotificationManagerCompat.IMPORTANCE_DEFAULT
        val channel = NotificationChannelCompat
            .Builder(NOTIFICATION_CHANNEL_ID, importance)
            .setName(name)
            .build()
        val notificationManager = NotificationManagerCompat.from(this)
        notificationManager.createNotificationChannel(channel)
    }
}