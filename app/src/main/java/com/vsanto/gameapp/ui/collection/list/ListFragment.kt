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
import androidx.recyclerview.widget.LinearLayoutManager
import com.vsanto.gameapp.databinding.FragmentListBinding
import com.vsanto.gameapp.domain.model.GameList
import com.vsanto.gameapp.ui.collection.list.adapters.ListAdapter
import com.vsanto.gameapp.ui.collection.list.dialogs.RemoveListDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private val listViewModel: ListViewModel by viewModels()

    private lateinit var listAdapter: ListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            navigateUp()
        }

        listViewModel.getLists()
        initUI()
    }

    private fun navigateUp() {
        findNavController().navigateUp()
    }

    private fun initUI() {
        initListeners()
        initAdapters()
        initUIState()
    }

    private fun initListeners() {
        binding.fabBack.setOnClickListener { navigateUp() }
        binding.fabAdd.setOnClickListener {
            navigateToNewList()
        }
    }

    private fun initAdapters() {
        listAdapter = ListAdapter(
            onItemSelected = { navigateToDetail(it) },
            onItemLongSelected = { openRemoveListDialog(it) }
        )
        binding.rvLists.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = listAdapter
        }
    }

    private fun navigateToNewList() {
        findNavController().navigate(ListFragmentDirections.actionListFragmentToNewListFragment())
    }

    private fun navigateToDetail(gameList: GameList) {
        findNavController().navigate(
            ListFragmentDirections.actionListFragmentToListDetailFragment(gameList.id)
        )
    }

    private fun openRemoveListDialog(gameList: GameList): Boolean {
        RemoveListDialogFragment(
            listTitle = gameList.title,
            onCancelSelected = {},
            onRemoveSelected = { removeList(gameList) }
        ).show(parentFragmentManager, "Remove List Dialog")

        return true
    }

    private fun removeList(gameList: GameList) {
        listViewModel.removeList(gameList.id)
        listAdapter.removeList(gameList)
    }

    private fun initUIState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                listViewModel.state.collect {
                    when (it) {
                        ListState.Loading -> loadingState()
                        is ListState.Error -> errorState()
                        is ListState.Success -> successState(it)
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

    private fun successState(state: ListState.Success) {
        binding.progressBar.isVisible = false

        val lists = state.lists
        listAdapter.updateList(lists.toMutableList())
    }

}