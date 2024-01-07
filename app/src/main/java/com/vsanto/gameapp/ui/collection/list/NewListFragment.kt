package com.vsanto.gameapp.ui.collection.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.vsanto.gameapp.databinding.FragmentNewListBinding
import com.vsanto.gameapp.ui.common.dialogs.DiscardDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewListFragment : Fragment() {

    private var _binding: FragmentNewListBinding? = null
    private val binding get() = _binding!!

    private val listViewModel: ListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            discardList()
        }

        initUI()
    }

    private fun initUI() {
        initListeners()
    }

    private fun initListeners() {
        binding.ivCreate.setOnClickListener { createList() }
        binding.ivCancel.setOnClickListener { discardList() }

    }

    private fun createList() {
        val name = binding.etName.text.toString()
        listViewModel.addList(name)
    }

    private fun discardList() {
        DiscardDialogFragment(
            onCancelSelected = {},
            onDiscardSelected = { navigateUp() }
        ).show(parentFragmentManager, "Discard Dialog")
    }

    private fun navigateUp() {
        findNavController().navigateUp()
    }

}