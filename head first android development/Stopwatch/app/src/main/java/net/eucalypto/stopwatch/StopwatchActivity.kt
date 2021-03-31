package net.eucalypto.stopwatch

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class StopwatchActivity : AppCompatActivity() {

    private var seconds = 0
    private var running = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stopwatch)

        runTimer()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        seconds = savedInstanceState.getInt(SECONDS_KEY)
        running = savedInstanceState.getBoolean(RUNNING_KEY)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(SECONDS_KEY, seconds)
        outState.putBoolean(RUNNING_KEY, running)
        super.onSaveInstanceState(outState)
    }

    private fun runTimer() {
        val timeView: TextView = findViewById(R.id.time_view)
        val handler = Handler(Looper.getMainLooper())

        handler.post(object : Runnable {

            override fun run() {
                val hours = seconds / 3600
                val minutes = (seconds % 3600) / 60
                val secs = seconds % 60
                val time = "%d:%02d:%02d".format(hours, minutes, secs)
                timeView.text = time

                if (running) {
                    seconds++
                }

                handler.postDelayed(this, 1000)
            }
        })
    }

    fun onClickStart(view: View) {
        running = true
    }

    fun onClickStop(view: View) {
        running = false
    }

    fun onClickReset(view: View) {
        running = false

        seconds = 0
    }

    companion object {
        const val SECONDS_KEY = "seconds"
        const val RUNNING_KEY = "running"
    }
}