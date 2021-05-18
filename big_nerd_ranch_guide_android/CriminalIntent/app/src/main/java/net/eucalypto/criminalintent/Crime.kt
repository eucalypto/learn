package net.eucalypto.criminalintent

import android.util.Log
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

private const val TAG = "Crime"

@Entity
data class Crime(
    @PrimaryKey
    val id: UUID = UUID.randomUUID(),
    var title: String = "",
    var date: Date = Date(),
    var isSolved: Boolean = false
) {
    init {
        Log.d(TAG, "Crime Data class object created: $this")
    }
}