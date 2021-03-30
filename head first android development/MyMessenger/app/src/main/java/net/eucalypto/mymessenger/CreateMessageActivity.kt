package net.eucalypto.mymessenger

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class CreateMessageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_message)
    }

    fun onSendMessage(view: View) {
        val editText: EditText = findViewById(R.id.message)
        val messageText = editText.text.toString()

        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, messageText)

        val chooserTitle = getString(R.string.chooser)
        val chosenIntent = Intent.createChooser(intent, chooserTitle)

        startActivity(chosenIntent)
    }
}