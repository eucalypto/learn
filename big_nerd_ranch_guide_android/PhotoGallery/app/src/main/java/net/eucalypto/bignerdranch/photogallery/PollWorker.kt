package net.eucalypto.bignerdranch.photogallery

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import timber.log.Timber

class PollWorker(val context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {
    override fun doWork(): Result {
        Timber.i("Work request triggered")
        return Result.success()
    }
}