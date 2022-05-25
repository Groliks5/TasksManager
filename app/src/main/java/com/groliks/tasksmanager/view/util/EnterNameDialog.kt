package com.groliks.tasksmanager.view.util

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.navArgs
import com.groliks.tasksmanager.R
import com.groliks.tasksmanager.databinding.DialogEnterNameBinding

class EnterNameDialog : DialogFragment(), DialogInterface.OnClickListener {
    private val navArgs: EnterNameDialogArgs by navArgs()
    private var _binding: DialogEnterNameBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = LayoutInflater.from(requireContext())
        _binding = DialogEnterNameBinding.inflate(inflater)

        binding.editName.setText(navArgs.name)

        return AlertDialog.Builder(requireContext())
            .setView(binding.root)
            .setPositiveButton(R.string.ok, this)
            .setNegativeButton(R.string.cancel, this)
            .create()
    }

    override fun onClick(dialog: DialogInterface?, which: Int) {
        if (which == DialogInterface.BUTTON_POSITIVE) {
            val bundle = bundleOf(NAME_KEY to binding.editName.text.toString())
            bundle.putInt(ID_KEY, navArgs.id)
            setFragmentResult(NEW_NAME_KEY, bundle)
        }
        dismiss()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        const val NEW_NAME_KEY = "new_name_key"
        const val NAME_KEY = "name_key"
        const val ID_KEY = "id_key"
        const val NEW_NAME_ID = -1
    }
}