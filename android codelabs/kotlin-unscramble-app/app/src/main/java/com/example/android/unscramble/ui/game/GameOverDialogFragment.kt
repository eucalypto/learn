package com.example.android.unscramble.ui.game

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.android.unscramble.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class GameOverDialogFragment : DialogFragment() {

    private val viewModel: GameViewModel by viewModels(ownerProducer = { requireParentFragment() })

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val parent = requireParentFragment() as GameFragment

        return MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.congratulations)
            .setMessage(getString(R.string.you_scored, viewModel.score.value))
            .setCancelable(false)
            .setNegativeButton(R.string.exit) { _, _ ->
                parent.exitGame()
            }
            .setPositiveButton(R.string.play_again) { _, _ ->
                parent.restartGame()
            }
            .create()
    }
}