package net.eucalypto.bignerdranch.sunset

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.animation.ArgbEvaluatorCompat
import net.eucalypto.bignerdranch.sunset.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var scene: View
    private lateinit var sun: View
    private lateinit var sky: View

    private val blueSkyColor: Int by lazy {
        ContextCompat.getColor(this, R.color.blue_sky)
    }
    private val sunsetSkyColor: Int by lazy {
        ContextCompat.getColor(this, R.color.sunset_sky)
    }
    private val nightSkyColor: Int by lazy {
        ContextCompat.getColor(this, R.color.night_sky)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        scene = binding.scene
        sun = binding.sun
        sky = binding.sky


        scene.setOnClickListener {
            startAnimation()
        }
    }

    private fun startAnimation() {
        val sunYStart = sun.top.toFloat()
        val sunYEnd = sky.height.toFloat()

        val heightAnimator = ObjectAnimator
            .ofFloat(sun, "y", sunYStart, sunYEnd)
            .setDuration(3000).apply {
                interpolator = AccelerateDecelerateInterpolator()
            }

        val sunsetSkyAnimator = ObjectAnimator
            .ofInt(sky, "backgroundColor", blueSkyColor, sunsetSkyColor)
            .setDuration(3000).apply {
                setEvaluator(ArgbEvaluatorCompat())
            }


        sunsetSkyAnimator.start()
        heightAnimator.start()
    }
}