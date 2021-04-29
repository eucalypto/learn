package net.eucalypto.criminalintent

import android.util.Log
import java.util.*

private const val TAG = "Crime"

data class Crime(
    val id: UUID = UUID.randomUUID(),
    var title: String = "",
    var date: Date = Date(),
    var isSolved: Boolean = false
) {
    init {
        Log.d(TAG, "Crime Data class object created: $this")
    }
}