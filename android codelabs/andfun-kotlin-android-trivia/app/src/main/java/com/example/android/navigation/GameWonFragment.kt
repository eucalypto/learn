/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.navigation

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.app.ShareCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.android.navigation.databinding.FragmentGameWonBinding


class GameWonFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val binding: FragmentGameWonBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_game_won, container, false
        )

        binding.nextMatchButton.setOnClickListener {
            findNavController().navigate(
                GameWonFragmentDirections
                    .actionGameWonFragmentToGameFragment()
            )
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = GameWonFragmentArgs.fromBundle(arguments!!)
        Toast.makeText(
            requireContext(),
            "Got arguments: ${args.numCorrect}, ${args.numQuestions}",
            Toast.LENGTH_LONG
        ).show()

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.winner_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.share -> {
                val args = GameWonFragmentArgs.fromBundle(arguments!!)

                val shareIntent =
                    ShareCompat.IntentBuilder.from(requireActivity())
                        .setText(
                            "Look at me! I have answered ${args.numQuestions} questions" +
                                    " of which I answered ${args.numCorrect} correctly"
                        )
                        .setType("text/plain")
                        .intent

                startActivity(Intent.createChooser(shareIntent, null))
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }


}
