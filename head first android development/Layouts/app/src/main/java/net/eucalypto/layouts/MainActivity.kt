package net.eucalypto.layouts

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_views_examples)
    }

    fun onTextViewClicked(view: View) {
        Toast
            .makeText(
                this,
                getString(R.string.text_view_clicked_toast),
                Toast.LENGTH_SHORT
            )
            .show()
        (view as TextView).text = getString(R.string.example_text_view_after_click)
    }

    fun onEditTextClicked(view: View) {
        val input = (view as EditText).text.toString()

        Toast.makeText(this, "You just typed: \"$input\"", Toast.LENGTH_LONG).show()

    }
}