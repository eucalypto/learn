package net.eucalypto.bignerdranch.beatbox

import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import net.eucalypto.bignerdranch.beatbox.databinding.ActivityMainBinding
import net.eucalypto.bignerdranch.beatbox.databinding.ListItemSoundBinding
import net.eucalypto.bignerdranch.beatbox.sound.Sound
import net.eucalypto.bignerdranch.beatbox.sound.SoundViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModelFactory = MainActivityViewModelFactory(assets)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(MainActivityViewModel::class.java)

        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(context, 3)
            adapter = SoundAdapter(viewModel.beatBox.sounds)
        }
    }

    private inner class SoundAdapter(private val sounds: List<Sound>) :
        RecyclerView.Adapter<SoundAdapter.SoundHolder>() {


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SoundHolder {
            val binding = DataBindingUtil.inflate<ListItemSoundBinding>(
                layoutInflater,
                R.layout.list_item_sound,
                parent,
                false
            )
            return SoundHolder(binding)
        }

        override fun onBindViewHolder(holder: SoundHolder, position: Int) {
            val sound = sounds[position]
            holder.bind(sound)
        }

        override fun getItemCount(): Int {
            return sounds.size
        }

        inner class SoundHolder(private val binding: ListItemSoundBinding) :
            RecyclerView.ViewHolder(binding.root) {
            init {
                binding.viewModel = SoundViewModel(viewModel.beatBox)
            }

            fun bind(sound: Sound) {
                binding.apply {
                    viewModel?.sound = sound
                    executePendingBindings()
                }
            }
        }

    }
}