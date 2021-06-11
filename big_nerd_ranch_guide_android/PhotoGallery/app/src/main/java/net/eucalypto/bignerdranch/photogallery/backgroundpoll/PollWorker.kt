package net.eucalypto.bignerdranch.photogallery.backgroundpoll

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import net.eucalypto.bignerdranch.photogallery.NOTIFICATION_CHANNEL_ID
import net.eucalypto.bignerdranch.photogallery.PhotoGalleryActivity
import net.eucalypto.bignerdranch.photogallery.R
import net.eucalypto.bignerdranch.photogallery.model.GalleryItem
import net.eucalypto.bignerdranch.photogallery.repository.FlickrFetcher
import net.eucalypto.bignerdranch.photogallery.repository.QueryPreferences
import timber.log.Timber

class PollWorker(private val context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {
    override fun doWork(): Result {
        val newestItems = fetchNewestItems()

        if (newestItems.isEmpty()) return Result.success()

        val storedResultId = QueryPreferences.getLastResultId(context)
        val fetchedResultId = newestItems.first().id
        if (fetchedResultId == storedResultId) {
            Timber.d("Got an old result: $fetchedResultId")
        } else {
            Timber.d("Got a new result: $fetchedResultId")
            QueryPreferences.setLastResultId(context, fetchedResultId)
            sendShowNotificationBroadcast()
        }

        return Result.success()
    }

    private fun sendShowNotificationBroadcast() {
        val notification = createNewPicturesPushNotification()
        val requestCode = 0
        val intent =
            NotificationReceiver.createIntent(requestCode, notification)
        context.sendOrderedBroadcast(intent, PERMISSION_PRIVATE)
    }

    private fun createNewPicturesPushNotification(): Notification {
        val photoGalleryIntent = PhotoGalleryActivity.newIntent(context)
        val pendingPhotoGalleryIntent =
            PendingIntent.getActivity(context, 0, photoGalleryIntent, 0)
        val resources = context.resources
        return NotificationCompat
            .Builder(context, NOTIFICATION_CHANNEL_ID)
            .setTicker(resources.getString(R.string.new_pictures_title))
            .setSmallIcon(android.R.drawable.ic_menu_report_image)
            .setContentTitle(resources.getString(R.string.new_pictures_title))
            .setContentText(resources.getString(R.string.new_pictures_text))
            .setContentIntent(pendingPhotoGalleryIntent)
            .setAutoCancel(true)
            .build()
    }

    private fun fetchNewestItems(): List<GalleryItem> {
        val query = QueryPreferences.getStoredQuery(context)
        return if (query.isEmpty()) {
            FlickrFetcher().fetchInterestingPhotosRequest()
                .execute().body()?.photos?.galleryItems
        } else {
            FlickrFetcher().fetchSearchPhotosRequest(query)
                .execute().body()?.photos?.galleryItems
        } ?: emptyList()
    }

    companion object {
        // This MUST be the very same string used also twice in AndroidManifest.xml
        const val PERMISSION_PRIVATE =
            "net.eucalypto.bignerdranch.photogallery.PRIVATE"
    }
}