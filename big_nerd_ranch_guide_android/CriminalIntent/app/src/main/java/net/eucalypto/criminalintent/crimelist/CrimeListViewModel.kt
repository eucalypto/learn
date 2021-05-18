package net.eucalypto.criminalintent.crimelist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import net.eucalypto.criminalintent.Crime
import net.eucalypto.criminalintent.CrimeRepository

class CrimeListViewModel : ViewModel() {

    private val crimeRepository = CrimeRepository.get()
    val crimeList: LiveData<List<Crime>> = crimeRepository.getCrimes()

}