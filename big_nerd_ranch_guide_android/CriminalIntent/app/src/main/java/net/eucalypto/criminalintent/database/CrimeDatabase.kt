package net.eucalypto.criminalintent.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import net.eucalypto.criminalintent.Crime

@Database(version = 1, entities = [Crime::class])
@TypeConverters(CrimeTypeConverters::class)
abstract class CrimeDatabase : RoomDatabase() {

    abstract val crimeDao: CrimeDao

}