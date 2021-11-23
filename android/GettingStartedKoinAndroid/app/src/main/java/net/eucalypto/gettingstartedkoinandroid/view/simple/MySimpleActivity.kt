package net.eucalypto.gettingstartedkoinandroid.view.simple

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import net.eucalypto.gettingstartedkoinandroid.databinding.SimpleActivityBinding
import org.koin.android.ext.android.inject

class MySimpleActivity : AppCompatActivity() {

    private val firstPresenter: MySimplePresenter by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = SimpleActivityBinding.inflate(layoutInflater)

        title = "MySimpleActiviy"
        binding.text.text = firstPresenter.sayHello()

        setContentView(binding.root)
    }
}