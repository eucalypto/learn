package net.eucalypto.bignerdranch.photogallery.backgroundpoll

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import net.eucalypto.bignerdranch.photogallery.*
import net.eucalypto.bignerdranch.photogallery.model.GalleryItem
import net.eucalypto.bignerdranch.photogallery.repository.FlickrFetcher
import net.eucalypto.bignerdranch.photogallery.repository.QueryPreferences
import timber.log.Timber

class PollWorker(private val context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {
    override fun doWork(): Result {
        val newestItems = getNewestItems()

        if (newestItems.isEmpty()) return Result.success()

        val lastResultId = QueryPreferences.getLastResultId(context)
        val resultId = newestItems.first().id
        if (resultId == lastResultId) {
            Timber.d("Got an old result: $resultId")
        } else {
            Timber.d("Got a new result: $resultId")
            QueryPreferences.setLastResultId(context, resultId)
            val notification = buildPushNotification()

            showBackgroundNotification(0, notification)
        }

        return Result.success()
    }

    private fun showBackgroundNotification(
        requestCode: Int,
        notification: Notification
    ) {
        val intent = Intent(ACTION_SHOW_NOTIFICATION).apply {
            putExtra(REQUEST_CODE, requestCode)
            putExtra(NOTIFICATION, notification)
        }

        context.sendOrderedBroadcast(intent, PERMISSION_PRIVATE)
    }

    private fun buildPushNotification(): Notification {
        val intent = PhotoGalleryActivity.newIntent(context)
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

        val resources = context.resources

        return NotificationCompat
            .Builder(context, NOTIFICATION_CHANNEL_ID)
            .setTicker(resources.getString(R.string.new_pictures_title))
            .setSmallIcon(android.R.drawable.ic_menu_report_image)
            .setContentTitle(resources.getString(R.string.new_pictures_title))
            .setContentText(resources.getString(R.string.new_pictures_text))
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()
    }

    private fun getNewestItems(): List<GalleryItem> {
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
        const val ACTION_SHOW_NOTIFICATION =
            "net.eucalypto.bignerdranch.photogallery.SHOW_NOTIFICATION"

        // This MUST be the very same string used also twice in AndroidManifest.xml
        const val PERMISSION_PRIVATE =
            "net.eucalypto.bignerdranch.photogallery.PRIVATE"

        const val REQUEST_CODE = "REQUEST_CODE"
        const val NOTIFICATION = "NOTIFICATION"
    }
}