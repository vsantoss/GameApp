package com.vsanto.gameapp.ui.collection

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.vsanto.gameapp.databinding.FragmentCollectionBinding
import com.vsanto.gameapp.domain.model.UserGameState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CollectionFragment : Fragment() {

    private var _binding: FragmentCollectionBinding? = null
    private val binding get() = _binding!!

    private val collectionViewModel: CollectionViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCollectionBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        collectionViewModel.getCollection()
        initUI()
    }

    private fun initUI() {
        initUIState()
    }

    private fun initUIState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                collectionViewModel.state.collect {
                    when (it) {
                        CollectionState.Loading -> loadingState()
                        is CollectionState.Error -> errorState()
                        is CollectionState.Success -> successState(it)
                    }
                }
            }
        }
    }

    private fun loadingState() {
        binding.progressBar.isVisible = true
    }

    private fun errorState() {
        binding.progressBar.isVisible = false
    }

    private fun successState(state: CollectionState.Success) {
        binding.progressBar.isVisible = false
        val games = state.library.games

        binding.tvPlayingValue.text = games
            .filter { g -> g.state == UserGameState.PLAYING }
            .size
            .toString()

        binding.tvPlayedValue.text = games
            .filter { g -> g.state == UserGameState.PLAYED }
            .size
            .toString()

        binding.tvWantValue.text = games
            .filter { g -> g.state == UserGameState.WANT }
            .size
            .toString()
    }

}