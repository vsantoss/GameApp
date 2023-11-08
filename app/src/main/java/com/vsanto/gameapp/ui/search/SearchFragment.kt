package com.vsanto.gameapp.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.vsanto.gameapp.domain.model.Game
import com.vsanto.gameapp.ui.search.adapters.GameAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private val searchViewModel by viewModels<SearchViewModel>()

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: GameAdapter

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
                searchViewModel.searchGame(query.orEmpty())
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun initUI() {
        initList()
        initUIState()
    }

    private fun initList() {
        adapter = GameAdapter { navigateToDetail(it) }
        binding.rvGames.setHasFixedSize(true)
        binding.rvGames.layoutManager = LinearLayoutManager(context)
        binding.rvGames.adapter = adapter
    }

    private fun navigateToDetail(game: Game) {
        findNavController().navigate(
            SearchFragmentDirections.actionSearchFragmentToGameDetailFragment(game)
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
    }

    private fun loadingState() {
        binding.progressBar.isVisible = true
    }

    private fun successState(state: SearchState.Success) {
        binding.progressBar.isVisible = false
        adapter.updateList(state.games)
    }

    private fun errorState(state: SearchState.Error) {
        binding.progressBar.isVisible = false
    }
}