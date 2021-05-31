package net.eucalypto.bignerdranch.beatbox.sound

import net.eucalypto.bignerdranch.beatbox.BeatBox
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class SoundViewModelTest {

    private lateinit var subject: SoundViewModel
    private lateinit var sound: Sound
    private lateinit var beatBox: BeatBox

    @Before
    fun setUp() {
        beatBox = mock(BeatBox::class.java)
        sound = Sound("assetPath")
        subject = SoundViewModel(beatBox).also {
            it.sound = sound
        }
    }

    @Test
    fun `exposes sound name as title`() {
        assertThat(subject.title, `is`(sound.name))
    }

    @Test
    fun `calls BeatBox play() on button clicked`() {
        subject.onButtonClicked()

        verify(beatBox).play(sound)
    }
}