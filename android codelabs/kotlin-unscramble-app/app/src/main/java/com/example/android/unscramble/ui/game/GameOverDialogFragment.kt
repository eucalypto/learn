package com.example.android.unscramble.ui.game

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.android.unscramble.R

class GameOverDialogFragment : DialogFragment() {

    private val viewModel: GameViewModel by activityViewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val parent = requireParentFragment() as GameFragment

        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.congratulations)
            .setMessage(getString(R.string.you_scored, viewModel.score))
            .setNegativeButton(R.string.exit) { _, _ ->
                parent.exitGame()
            }
            .setPositiveButton(R.string.play_again) { _, _ ->
                parent.restartGame()
            }
            .create()
    }
}