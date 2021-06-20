package net.eucalypto.sandbox.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.eucalypto.sandbox.databinding.MainFragmentBinding
import timber.log.Timber

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return MainFragmentBinding.inflate(inflater, container, false).root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = MainFragmentBinding.bind(view)


        GlobalScope.launch {
            Timber.d("GlobalScope.launch{} : ${Thread.currentThread().name}")
        }

        lifecycleScope.launch {
            Timber.d("lifecycleScope.launch{} : ${Thread.currentThread().name}")
            launch {
                delay(2000)
                Timber.d("launch inside of coroutine : ${Thread.currentThread().name}")
            }

            Timber.d("lifecycleScope.launch{} end")
        }

        viewModel.testCoroutineScopes()
    }

}