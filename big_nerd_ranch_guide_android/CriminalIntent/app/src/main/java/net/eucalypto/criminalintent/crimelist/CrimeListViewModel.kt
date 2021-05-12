package net.eucalypto.criminalintent.crimelist

import androidx.lifecycle.ViewModel
import net.eucalypto.criminalintent.Crime

class CrimeListViewModel : ViewModel() {

    val crimes = mutableListOf<Crime>()

    val crimeCount
        get() = crimes.size

    init {
        for (i in 0 until 100) {
            crimes += Crime(title = "Crime #$i", isSolved = i % 2 == 0)
        }
    }
}