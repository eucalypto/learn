package net.eucalypto.bignerdranch.photogallery.ui.photopage

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import net.eucalypto.bignerdranch.photogallery.R

class PhotoPageActivity : AppCompatActivity() {

    lateinit var fragment: PhotoPageFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_page)

        val fm = supportFragmentManager
        val currentFragment =
            fm.findFragmentById(R.id.fragment_container_photo_page)

        if (currentFragment == null) {
            val fragment = PhotoPageFragment.newInstance(intent.data!!)
            this.fragment = fragment
            fm.beginTransaction()
                .add(R.id.fragment_container_photo_page, fragment)
                .commit()
        } else {
            fragment = currentFragment as PhotoPageFragment
        }
    }

    override fun onBackPressed() {
        if (!fragment.successfullyNavigatedBack()) {
            super.onBackPressed()
        }
    }


    companion object {
        fun newIntent(context: Context, photoPageUri: Uri): Intent {
            return Intent(context, PhotoPageActivity::class.java).apply {
                data = photoPageUri
            }
        }
    }
}