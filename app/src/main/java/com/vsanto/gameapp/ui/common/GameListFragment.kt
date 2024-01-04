package com.vsanto.gameapp.ui.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.vsanto.gameapp.databinding.FragmentGameListBinding
import com.vsanto.gameapp.domain.model.GameSummary
import com.vsanto.gameapp.ui.common.adapters.GameListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class GameListFragment : Fragment() {

    private var _binding: FragmentGameListBinding? = null
    private val binding get() = _binding!!
    private val args: GameListFragmentArgs by navArgs()
    private val gameListViewModel: GameListViewModel by viewModels()

    private lateinit var title: String

    private lateinit var gameListAdapter: GameListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameListBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            navigateUp()
        }

        title = args.title
        gameListViewModel.getGames(args.gameIds)

        initUI()
    }

    private fun navigateUp() {
        findNavController().navigateUp()
    }

    private fun navigateToDetail(game: GameSummary) {
        findNavController().navigate(
            GameListFragmentDirections.actionGameListFragmentToGameDetailFragment(game.id)
        )
    }

    private fun initUI() {
        initAdapters()
        initListeners()
        initUIState()
    }

    private fun initAdapters() {
        gameListAdapter = GameListAdapter { navigateToDetail(it) }
        binding.rvGameList.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(context, 4)
            adapter = gameListAdapter
        }
    }

    private fun initListeners() {
        binding.fabBack.setOnClickListener { navigateUp() }
    }

    private fun initUIState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                gameListViewModel.state.collect {
                    when (it) {
                        GameListState.Loading -> loadingState()
                        is GameListState.Error -> errorState()
                        is GameListState.Success -> successState(it)
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

    private fun successState(state: GameListState.Success) {
        binding.progressBar.isVisible = false

        val games = state.games

        binding.tvTitle.text = title
        binding.tvSize.text = games.size.toString() + " games"
        gameListAdapter.updateList(games)
    }

}