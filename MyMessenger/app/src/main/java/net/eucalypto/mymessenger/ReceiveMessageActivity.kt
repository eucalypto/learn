package net.eucalypto.mymessenger

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ReceiveMessageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receive_message)

        val messageText = intent.getStringExtra(EXTRA_MESSAGE)
        val messageView: TextView = findViewById(R.id.message)

        messageView.text = messageText
    }

    companion object {
        const val EXTRA_MESSAGE = "message"
    }
}