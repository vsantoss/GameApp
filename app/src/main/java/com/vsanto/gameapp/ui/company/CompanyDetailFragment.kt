package com.vsanto.gameapp.ui.company

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
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import com.vsanto.gameapp.R
import com.vsanto.gameapp.databinding.FragmentCompanyDetailBinding
import com.vsanto.gameapp.domain.model.CompanyDetail
import com.vsanto.gameapp.domain.model.GameSummary
import com.vsanto.gameapp.domain.model.Website
import com.vsanto.gameapp.ui.common.adapters.WebsiteAdapter
import com.vsanto.gameapp.ui.company.adapters.ProducedGameAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat

@AndroidEntryPoint
class CompanyDetailFragment : Fragment() {

    private var _binding: FragmentCompanyDetailBinding? = null
    private val binding get() = _binding!!
    private val args: CompanyDetailFragmentArgs by navArgs()
    private val companyDetailViewModel: CompanyDetailViewModel by viewModels()

    private lateinit var developedGameAdapter: ProducedGameAdapter
    private lateinit var publishedGameAdapter: ProducedGameAdapter
    private lateinit var websiteAdapter: WebsiteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCompanyDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        companyDetailViewModel.getCompany(args.id)
        initUIState()
    }

    private fun initUIState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                companyDetailViewModel.state.collect {
                    when (it) {
                        is CompanyDetailState.Error -> errorState()
                        CompanyDetailState.Loading -> loadingState()
                        is CompanyDetailState.Success -> successState(it)
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

    private fun successState(state: CompanyDetailState.Success) {
        binding.progressBar.isVisible = false
        val company = state.company

        loadLogo(company)

        binding.tvName.text = company.name

        if (company.createDate != null) {
            val formatter = SimpleDateFormat("dd MMMM yyyy")
            binding.tvCreateDate.text = formatter.format(company.createDate)
        }

        if (company.description.isNotEmpty()) {
            binding.tvDescription.text = company.description
        } else {
            binding.tvDescription.isVisible = false
        }


        val developedGamesSize = company.developedGames?.size ?: 0
        val publishedGamesSize = company.publishedGames?.size ?: 0
        binding.tvDevelopedTitle.text = getString(R.string.developed_games, developedGamesSize)
        binding.tvPublishedTitle.text = getString(R.string.published_games, publishedGamesSize)
        initDevelopedGames(company.developedGames)
        initPublishedGames(company.publishedGames)

        initWebsites(company.websites)
    }

    private fun loadLogo(company: CompanyDetail) {
        if (company.logo != null) {
            Picasso.get().isLoggingEnabled = true
            Picasso.get().load(company.logo.url).into(binding.ivLogo)
        } else {
            binding.rlLogo.isVisible = false
        }
    }

    private fun initDevelopedGames(games: List<GameSummary>?) {
        developedGameAdapter = ProducedGameAdapter(games.orEmpty()) { navigateToProducedGame(it) }
        binding.rvDeveloped.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = developedGameAdapter
        }
    }

    private fun initPublishedGames(games: List<GameSummary>?) {
        publishedGameAdapter = ProducedGameAdapter(games.orEmpty()) { navigateToProducedGame(it) }
        binding.rvPublished.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = publishedGameAdapter
        }
    }

    private fun navigateToProducedGame(id: Int) {
        findNavController().navigate(
            CompanyDetailFragmentDirections.actionCompanyDetailFragmentToGameDetailFragment(id)
        )
    }

    private fun initWebsites(websites: List<Website>?) {
        websiteAdapter = WebsiteAdapter(websites.orEmpty())
        binding.rvWebsites.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(context, 3)
            adapter = websiteAdapter
        }
    }

}