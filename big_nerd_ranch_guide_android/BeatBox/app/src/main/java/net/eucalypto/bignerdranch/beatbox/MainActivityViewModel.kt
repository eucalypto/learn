package net.eucalypto.bignerdranch.beatbox

import android.content.res.AssetManager
import androidx.lifecycle.ViewModel

class MainActivityViewModel(assets: AssetManager) : ViewModel() {

    val beatBox = BeatBox(assets)


    override fun onCleared() {
        super.onCleared()
        beatBox.release()
    }

}