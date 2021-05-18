package net.eucalypto.criminalintent.crimelist

import androidx.lifecycle.ViewModel
import net.eucalypto.criminalintent.CrimeRepository

class CrimeListViewModel : ViewModel() {

    private val crimeRepository = CrimeRepository.get()
    val crimeListLiveData = crimeRepository.getCrimes()

}