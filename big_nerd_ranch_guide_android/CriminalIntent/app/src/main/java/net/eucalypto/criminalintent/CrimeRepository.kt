package net.eucalypto.criminalintent

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import net.eucalypto.criminalintent.database.CrimeDatabase
import net.eucalypto.criminalintent.database.migration_1_2
import java.util.*
import java.util.concurrent.Executors

private const val DATABASE_NAME = "crime-database"

class CrimeRepository private constructor(context: Context) {

    private val database: CrimeDatabase =
        Room.databaseBuilder(context.applicationContext, CrimeDatabase::class.java, DATABASE_NAME)
            .addMigrations(migration_1_2)
            .build()

    private val crimeDao = database.crimeDao

    fun getCrimes(): LiveData<List<Crime>> = crimeDao.getCrimes()

    fun getCrime(uuid: UUID): LiveData<Crime?> = crimeDao.getCrime(uuid)


    private val executor = Executors.newSingleThreadExecutor()

    fun updateCrime(crime: Crime) {
        executor.execute {
            crimeDao.updateCrime(crime)
        }
    }

    fun insertCrime(crime: Crime) {
        executor.execute {
            crimeDao.insertCrime(crime)
        }
    }


    companion object {
        private var INSTANCE: CrimeRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = CrimeRepository(context)
            }
        }

        fun get(): CrimeRepository {
            return INSTANCE
                ?: throw IllegalStateException("CrimeRepository must be initialized before use")
        }
    }
}