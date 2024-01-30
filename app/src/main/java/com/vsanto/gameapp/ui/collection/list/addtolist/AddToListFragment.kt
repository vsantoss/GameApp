package com.vsanto.gameapp.ui.collection.list.addtolist

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
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.vsanto.gameapp.databinding.FragmentAddToListBinding
import com.vsanto.gameapp.domain.model.GameList
import com.vsanto.gameapp.ui.collection.list.ListState
import com.vsanto.gameapp.ui.collection.list.ListViewModel
import com.vsanto.gameapp.ui.collection.list.addtolist.adapters.AddListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddToListFragment : Fragment() {

    private var _binding: FragmentAddToListBinding? = null
    private val binding get() = _binding!!

    private val args: AddToListFragmentArgs by navArgs()
    private val listViewModel: ListViewModel by viewModels()

    private lateinit var addListAdapter: AddListAdapter

    private var gameId: Int = -1
    private val selectedLists = mutableListOf<GameList>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddToListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            cancelAdd()
        }

        gameId = args.gameId

        listViewModel.getLists()
        initUI()
    }

    private fun initUI() {
        initListeners()
        initAdapters()
        initUIState()
    }

    private fun initListeners() {
        binding.ivCancel.setOnClickListener { navigateUp() }
        binding.ivAdd.setOnClickListener { addToLists(gameId, selectedLists.toList()) }
    }

    private fun initAdapters() {
        addListAdapter = AddListAdapter(gameId) { selectList(it) }

        binding.rvlists.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = addListAdapter
        }
    }

    private fun selectList(gameList: GameList) {
        if (selectedLists.contains(gameList)) {
            selectedLists.remove(gameList)
        } else {
            selectedLists.add(gameList)
        }
    }

    private fun cancelAdd() {
        navigateUp()
    }

    private fun addToLists(gameId: Int, lists: List<GameList>) {
        listViewModel.addToLists(gameId, lists)
    }

    private fun navigateUp() {
        findNavController().navigateUp()
    }

    private fun initUIState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                listViewModel.state.collect {
                    when (it) {
                        ListState.Loading -> loadingState()
                        is ListState.Error -> errorState(it.error)
                        is ListState.Success -> successState(it)
                    }
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                listViewModel.addState.collect {
                    when (it) {
                        AddToListState.Init -> initState()
                        AddToListState.Loading -> loadingState()
                        is AddToListState.Error -> errorState(it.error)
                        is AddToListState.Success -> navigateUp()
                    }
                }
            }
        }
    }

    private fun initState() {
        binding.progressBar.isVisible = false
        binding.cvError.isVisible = false
    }

    private fun loadingState() {
        binding.progressBar.isVisible = true
        binding.cvError.isVisible = false
    }

    private fun errorState(error: String) {
        binding.progressBar.isVisible = false
        binding.cvError.isVisible = true
        binding.tvError.text = error
    }

    private fun successState(state: ListState.Success) {
        binding.progressBar.isVisible = false

        addListAdapter.updateList(state.lists)
    }

}
