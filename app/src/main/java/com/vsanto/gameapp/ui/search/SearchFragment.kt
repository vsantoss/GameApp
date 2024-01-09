package com.vsanto.gameapp.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.vsanto.gameapp.databinding.FragmentSearchBinding
import com.vsanto.gameapp.domain.model.GameSummary
import com.vsanto.gameapp.domain.model.RecentSearch
import com.vsanto.gameapp.ui.search.adapters.GameResultAdapter
import com.vsanto.gameapp.ui.search.adapters.RecentSearchAdapter
import com.vsanto.gameapp.ui.search.dialogs.RemoveSearchDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private val searchViewModel by viewModels<SearchViewModel>()
    private val recentSearchViewModel by viewModels<RecentSearchViewModel>()

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var recentSearchAdapter: RecentSearchAdapter
    private lateinit var gameResultAdapter: GameResultAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recentSearchViewModel.getRecentSearches()

        initUI()
    }

    private fun initUI() {
        initListeners()
        initAdapters()
        initUIState()
    }

    private fun initListeners() {
        binding.svGame.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchGame(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        val svCloseBtn: ImageView =
            binding.svGame.findViewById(androidx.appcompat.R.id.search_close_btn)

        svCloseBtn.setOnClickListener {
            cleanSearch()
        }
    }

    private fun searchGame(query: String?) {
        recentSearchViewModel.addRecentSearch(query.orEmpty())
        searchViewModel.searchGame(query.orEmpty())
    }

    private fun cleanSearch() {
        binding.svGame.setQuery("", false)
        binding.svGame.clearFocus()

        gameResultAdapter.updateList(emptyList())
        recentSearchViewModel.getRecentSearches()

        binding.llSearches.isVisible = true
        binding.rvGames.isVisible = false
    }

    private fun initAdapters() {
        recentSearchAdapter =
            RecentSearchAdapter(
                onItemSelected = { searchGameByRecentSearch(it) },
                onItemLongSelected = { openRemoveSearchDialog(it) }
            )
        binding.rvSearches.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = recentSearchAdapter
        }

        gameResultAdapter = GameResultAdapter { navigateToDetail(it) }
        binding.rvGames.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = gameResultAdapter
        }
    }

    private fun searchGameByRecentSearch(recentSearch: RecentSearch) {
        binding.svGame.setQuery(recentSearch.query, false)
        binding.svGame.clearFocus()

        recentSearchViewModel.removeRecentSearch(recentSearch.id)
        searchGame(recentSearch.query)
    }

    private fun openRemoveSearchDialog(recentSearch: RecentSearch): Boolean {
        RemoveSearchDialogFragment(
            query = recentSearch.query,
            onCancelSelected = {},
            onRemoveSelected = { removeRecentSearch(recentSearch) }
        ).show(parentFragmentManager, "Remove Search Dialog")

        return true
    }

    private fun removeRecentSearch(recentSearch: RecentSearch) {
        recentSearchViewModel.removeRecentSearch(recentSearch.id)
        recentSearchAdapter.removeSearch(recentSearch)
    }

    private fun navigateToDetail(game: GameSummary) {
        findNavController().navigate(
            SearchFragmentDirections.actionSearchFragmentToGameDetailFragment(game.id)
        )
    }

    private fun initUIState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                searchViewModel.state.collect {
                    when (it) {
                        SearchState.Init -> initState()
                        SearchState.Loading -> loadingSearchGameState()
                        is SearchState.Success -> successSearchGameState(it)
                        is SearchState.Error -> errorState()
                    }
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                recentSearchViewModel.state.collect {
                    when (it) {
                        RecentSearchState.Init -> initState()
                        RecentSearchState.Loading -> loadingRecentSearchState()
                        is RecentSearchState.Success -> successRecentSearchState(it)
                        is RecentSearchState.Error -> errorState()
                    }
                }
            }
        }
    }

    private fun initState() {
        binding.progressBar.isVisible = false
        showSearches()
    }

    private fun loadingSearchGameState() {
        binding.progressBar.isVisible = true
        binding.llSearches.isVisible = false
        binding.rvGames.isVisible = false
    }

    private fun loadingRecentSearchState() {
        binding.progressBar.isVisible = false
        showSearches()
    }

    private fun successSearchGameState(state: SearchState.Success) {
        binding.progressBar.isVisible = false
        showResults()

        gameResultAdapter.updateList(state.games.sortedByDescending { it.releaseDate })
    }

    private fun successRecentSearchState(state: RecentSearchState.Success) {
        binding.progressBar.isVisible = false
        showSearches()

        recentSearchAdapter.updateList(state.searches.toMutableList())
    }

    private fun errorState() {
        binding.progressBar.isVisible = false
    }

    private fun showResults() {
        binding.rvGames.isVisible = true
    }

    private fun showSearches() {
        binding.llSearches.isVisible = true
    }

}