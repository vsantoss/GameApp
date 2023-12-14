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
import com.vsanto.gameapp.ui.search.adapters.GameAdapter
import com.vsanto.gameapp.ui.search.adapters.SearchAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private val searchViewModel by viewModels<SearchViewModel>()

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var searchAdapter: SearchAdapter
    private lateinit var gameAdapter: GameAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        initUI()
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
            binding.svGame.setQuery("", false)
            binding.svGame.clearFocus()

            gameAdapter.updateList(emptyList())
            binding.llSearches.isVisible = true
            binding.rvGames.isVisible = false
        }
    }

    private fun searchGame(query: String?) {
        searchAdapter.add(query)
        searchViewModel.searchGame(query.orEmpty())
    }

    private fun initUI() {
        initList()
        initUIState()
    }

    private fun initList() {
        searchAdapter = SearchAdapter { searchGameByRecentSearch(it) }
        binding.rvSearches.setHasFixedSize(true)
        binding.rvSearches.layoutManager = LinearLayoutManager(context)
        binding.rvSearches.adapter = searchAdapter

        gameAdapter = GameAdapter { navigateToDetail(it) }
        binding.rvGames.setHasFixedSize(true)
        binding.rvGames.layoutManager = LinearLayoutManager(context)
        binding.rvGames.adapter = gameAdapter
    }

    private fun searchGameByRecentSearch(query: String) {
        binding.svGame.setQuery(query, false)
        binding.svGame.clearFocus()
        searchGame(query)
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
                        SearchState.Loading -> loadingState()
                        is SearchState.Success -> successState(it)
                        is SearchState.Error -> errorState(it)
                    }
                }
            }
        }
    }

    private fun initState() {
        binding.progressBar.isVisible = false
        binding.llSearches.isVisible = true
        binding.rvGames.isVisible = false
    }

    private fun loadingState() {
        binding.llSearches.isVisible = false
        binding.progressBar.isVisible = true
    }

    private fun successState(state: SearchState.Success) {
        binding.llSearches.isVisible = false
        binding.progressBar.isVisible = false
        gameAdapter.updateList(state.games.sortedByDescending { it.releaseDate })
        binding.rvGames.isVisible = true
    }

    private fun errorState(state: SearchState.Error) {
        binding.progressBar.isVisible = false
    }

}