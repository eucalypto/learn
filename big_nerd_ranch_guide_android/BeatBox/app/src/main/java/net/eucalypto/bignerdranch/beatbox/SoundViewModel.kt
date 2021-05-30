package net.eucalypto.bignerdranch.beatbox

class SoundViewModel {
    var sound: Sound? = null

    val title: String?
        get() = sound?.name
}