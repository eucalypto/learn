package net.eucalypto.bignerdranch.beatbox.sound

import org.junit.Assert.assertEquals
import org.junit.Test

class SoundTest {

    @Test
    fun foo() {
        val sound = Sound("sample_sounds/65_cjipie.wav")
        assertEquals("65_cjipie", sound.name)
    }

}