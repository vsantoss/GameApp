package com.vsanto.gameapp.ui.collection.list.detail

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
import androidx.recyclerview.widget.GridLayoutManager
import com.vsanto.gameapp.R
import com.vsanto.gameapp.databinding.FragmentListDetailBinding
import com.vsanto.gameapp.domain.model.GameList
import com.vsanto.gameapp.domain.model.GameSummary
import com.vsanto.gameapp.ui.collection.list.dialogs.RemoveListDialogFragment
import com.vsanto.gameapp.ui.common.gamelist.adapters.GameListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListDetailFragment : Fragment() {

    private var _binding: FragmentListDetailBinding? = null
    private val binding get() = _binding!!

    private val args: ListDetailFragmentArgs by navArgs()
    private val listDetailViewModel: ListDetailViewModel by viewModels()

    private lateinit var gameListAdapter: GameListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            navigateUp()
        }

        listDetailViewModel.getList(args.id)
        initUI()
    }

    private fun navigateUp() {
        findNavController().popBackStack(R.id.listFragment, false)
    }

    private fun navigateToDetail(game: GameSummary) {
        findNavController().navigate(
            ListDetailFragmentDirections.actionListDetailFragmentToGameDetailFragment(game.id)
        )
    }

    private fun initUI() {
        initAdapters()
        initListeners()
        initUIState()
    }

    private fun initAdapters() {
        gameListAdapter = GameListAdapter { navigateToDetail(it) }
        binding.rvGames.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(context, 4)
            adapter = gameListAdapter
        }
    }

    private fun initListeners() {
        binding.fabBack.setOnClickListener { navigateUp() }

        binding.tvDescription.setOnClickListener {
            binding.tvDescription.isSelected = !binding.tvDescription.isSelected
            if (binding.tvDescription.isSelected) {
                binding.tvDescription.maxLines =
                    context?.resources?.getInteger(R.integer.summary_max_lines_selected) ?: 100
            } else {
                binding.tvDescription.maxLines =
                    context?.resources?.getInteger(R.integer.summary_max_lines) ?: 4
            }
        }

    }

    private fun initUIState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                listDetailViewModel.state.collect {
                    when (it) {
                        ListDetailState.Loading -> loadingState()
                        is ListDetailState.Error -> errorState()
                        is ListDetailState.Success -> successState(it.list)
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

    private fun successState(list: GameList) {
        binding.progressBar.isVisible = false

        binding.tvTitle.text = list.title

        if (list.description.isNotEmpty()) {
            binding.tvDescription.text = list.description
        } else {
            binding.tvDescription.isVisible = false
        }

        gameListAdapter.updateList(list.games)

        binding.ivDelete.setOnClickListener { openRemoveListDialog(list) }
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
        listDetailViewModel.removeList(gameList.id)
        navigateUp()
    }

}