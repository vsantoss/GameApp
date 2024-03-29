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
import androidx.navigation.fragment.findNavController
import com.vsanto.gameapp.databinding.FragmentCollectionBinding
import com.vsanto.gameapp.domain.model.GameList
import com.vsanto.gameapp.domain.model.UserGame
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
        val lists = state.library.lists

        val playingGames = games.filter { g -> g.state == UserGameState.PLAYING }
        val playedGames = games.filter { g -> g.state == UserGameState.PLAYED }
        val wantGames = games.filter { g -> g.state == UserGameState.WANT }

        initPlayingGames(playingGames)
        initPlayedGames(playedGames)
        initWantGames(wantGames)

        initLists(lists)
    }

    private fun initPlayingGames(games: List<UserGame>) {
        binding.clPlaying.setOnClickListener {
            navigateToGameList(games, "Playing Games")
        }
        binding.tvPlayingValue.text = games.size.toString()
    }

    private fun initPlayedGames(games: List<UserGame>) {
        binding.clPlayed.setOnClickListener {
            navigateToGameList(games, "Played Games")
        }
        binding.tvPlayedValue.text = games.size.toString()
    }

    private fun initWantGames(games: List<UserGame>) {
        binding.clWant.setOnClickListener {
            navigateToGameList(games, "Want Games")
        }
        binding.tvWantValue.text = games.size.toString()
    }

    private fun initLists(lists: List<GameList>) {
        binding.clLists.setOnClickListener {
            navigateToLists()
        }
        binding.tvListsValue.text = lists.size.toString()
    }

    private fun navigateToGameList(games: List<UserGame>, title: String) {
        findNavController().navigate(
            CollectionFragmentDirections.actionCollectionFragmentToGameListFragment(
                games.map { it.gameId }.toIntArray(),
                title
            )
        )
    }

    private fun navigateToLists() {
        findNavController().navigate(
            CollectionFragmentDirections.actionCollectionFragmentToListFragment()
        )
    }

}