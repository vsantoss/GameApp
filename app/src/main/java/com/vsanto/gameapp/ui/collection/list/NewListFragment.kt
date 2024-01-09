package com.vsanto.gameapp.ui.collection.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.vsanto.gameapp.databinding.FragmentNewListBinding
import com.vsanto.gameapp.ui.common.dialogs.DiscardDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

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
        initUIState()
    }

    private fun initListeners() {
        binding.ivCreate.setOnClickListener { createList() }
        binding.ivCancel.setOnClickListener { discardList() }

    }

    private fun initUIState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                listViewModel.newState.collect {
                    when (it) {
                        NewListState.Init -> initState()
                        NewListState.Loading -> loadingState()
                        is NewListState.Error -> errorState()
                        is NewListState.Success -> successState(it)
                    }
                }
            }
        }
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

    private fun navigateToDetail(id: Int) {
        findNavController().navigate(
            NewListFragmentDirections.actionNewListFragmentToListDetailFragment(id)
        )
    }

    private fun initState() {
        binding.progressBar.isVisible = false
    }

    private fun loadingState() {
        binding.progressBar.isVisible = true
    }

    private fun errorState() {
        binding.progressBar.isVisible = false
    }

    private fun successState(state: NewListState.Success) {
        binding.progressBar.isVisible = false
        navigateToDetail(state.id)
    }

}