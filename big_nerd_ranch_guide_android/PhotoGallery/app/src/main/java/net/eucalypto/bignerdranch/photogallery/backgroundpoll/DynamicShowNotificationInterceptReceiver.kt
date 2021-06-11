package net.eucalypto.bignerdranch.photogallery.backgroundpoll

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import timber.log.Timber

class DynamicShowNotificationInterceptReceiver private constructor(private val fragment: Fragment) :
    DefaultLifecycleObserver {

    companion object {
        fun setUpWith(fragment: Fragment) {
            DynamicShowNotificationInterceptReceiver(fragment)
        }
    }

    init {
        fragment.lifecycle.addObserver(this)
    }

    private val disableNotification = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            Timber.d("Got a broadcast: ${intent.action}. Cancelling further notifications")
            resultCode = Activity.RESULT_CANCELED
        }
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        val filter = IntentFilter(NotificationReceiver.ACTION_SHOW_NOTIFICATION)
        fragment.requireActivity().registerReceiver(
            disableNotification,
            filter,
            PollWorker.PERMISSION_PRIVATE,
            null
        )
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        fragment.requireActivity().unregisterReceiver(disableNotification)
    }
}