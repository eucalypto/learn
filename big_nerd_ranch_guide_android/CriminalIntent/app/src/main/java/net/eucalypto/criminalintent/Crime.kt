package net.eucalypto.criminalintent

import androidx.room.Entity
import androidx.room.PrimaryKey
import timber.log.Timber
import java.util.*

@Entity
data class Crime(
    @PrimaryKey
    val id: UUID = UUID.randomUUID(),
    var title: String = "",
    var date: Date = Date(),
    var isSolved: Boolean = false
) {
    init {
        Timber.d("Crime Data class object created: $this")
    }
}