package com.vsanto.gameapp.ui.game

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
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
import com.vsanto.gameapp.databinding.FragmentGameDetailBinding
import com.vsanto.gameapp.domain.model.GameDetail
import com.vsanto.gameapp.domain.model.Image
import com.vsanto.gameapp.domain.model.InvolvedCompany
import com.vsanto.gameapp.domain.model.SimilarGame
import com.vsanto.gameapp.domain.model.Website
import com.vsanto.gameapp.ui.game.adapters.CompanyAdapter
import com.vsanto.gameapp.ui.game.adapters.ScreenshotAdapter
import com.vsanto.gameapp.ui.game.adapters.SimilarGameAdapter
import com.vsanto.gameapp.ui.common.adapters.WebsiteAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GameDetailFragment : Fragment() {

    private var _binding: FragmentGameDetailBinding? = null
    private val binding get() = _binding!!
    private val args: GameDetailFragmentArgs by navArgs()
    private val gameDetailViewModel: GameDetailViewModel by viewModels()

    private lateinit var screenshotAdapter: ScreenshotAdapter
    private lateinit var companyAdapter: CompanyAdapter
    private lateinit var similarGameAdapter: SimilarGameAdapter
    private lateinit var websiteAdapter: WebsiteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gameDetailViewModel.getGame(args.id)
        initUIState()
    }

    private fun initUIState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                gameDetailViewModel.state.collect {
                    when (it) {
                        is GameDetailState.Error -> errorState()
                        GameDetailState.Loading -> loadingState()
                        is GameDetailState.Success -> successState(it)
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

    private fun successState(state: GameDetailState.Success) {
        binding.progressBar.isVisible = false
        val game = state.game

        loadLogo(game)

        binding.tvName.text = game.name

        val developer: InvolvedCompany? = getDeveloperCompany(game)
        binding.tvDeveloper.text = developer?.name.orEmpty()
        binding.tvReleaseDate.text = game.releaseDate
        initRating(game.rating)

        binding.tvSummary.text = game.summary
        binding.tvGenres.text = toString(game.genres)
        binding.tvThemes.text = toString(game.themes)
        binding.tvGameModes.text = toString(game.modes)
        binding.tvPlayerPerspectives.text = toString(game.playerPerspectives)
        binding.tvPlatforms.text = toString(game.platforms)

        initScreenshots(game.screenshots)
        initInvolvedCompanies(game.involvedCompanies)
        initSimilarGames(game.similarGames)
        initWebsites(game.websites)
    }

    private fun initRating(rating: Double) {
        val parsedRating = String.format("%.1f", rating / 10).replace(",", ".").toDouble()
        binding.tvRating.text = parsedRating.toString()

        val color = when (parsedRating) {
            in 0.01..2.00 -> {
                R.color.E_rating
            }

            in 2.01..4.00 -> {
                R.color.D_rating
            }

            in 4.01..6.00 -> {
                R.color.C_rating
            }

            in 6.01..8.00 -> {
                R.color.B_rating
            }

            in 8.01..10.00 -> {
                R.color.A_rating
            }

            else -> {
                binding.tvRating.text = "N/A"
                R.color.N_A_rating
            }
        }

        binding.cvRating.setCardBackgroundColor(
            ContextCompat.getColor(requireContext(), color)
        )
    }

    private fun initScreenshots(screenshots: List<Image>?) {
        screenshotAdapter = ScreenshotAdapter(screenshots.orEmpty())
        binding.rvScreenshots.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = screenshotAdapter
        }
    }

    private fun initInvolvedCompanies(involvedCompanies: List<InvolvedCompany>?) {
        companyAdapter = CompanyAdapter(involvedCompanies.orEmpty()) { navigateToCompany(it) }
        binding.rvInvolvedCompanies.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = companyAdapter
        }
    }

    private fun initSimilarGames(similarGames: List<SimilarGame>?) {
        similarGameAdapter = SimilarGameAdapter(similarGames.orEmpty()) { navigateToOtherGame(it) }
        binding.rvSimilarGames.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = similarGameAdapter
        }
    }

    private fun initWebsites(websites: List<Website>?) {
        websiteAdapter = WebsiteAdapter(websites.orEmpty())
        binding.rvWebsites.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(context, 3)
            adapter = websiteAdapter
        }
    }

    private fun navigateToCompany(id: Int) {
        findNavController().navigate(
            GameDetailFragmentDirections.actionGameDetailFragmentToCompanyDetailFragment(id)
        )
    }

    private fun navigateToOtherGame(id: Int) {
        findNavController().navigate(
            GameDetailFragmentDirections.actionGameDetailFragmentSelf(id)
        )
    }

    private fun getDeveloperCompany(game: GameDetail): InvolvedCompany? {
        return game.involvedCompanies?.firstOrNull { it.developer }
    }

    private fun loadLogo(game: GameDetail) {
        if (game.cover != null) {
            Picasso.get().isLoggingEnabled = true
            Picasso.get().load(game.cover.url).into(binding.ivLogo)
        }
    }

    private fun toString(list: List<String>?): String {
        if (list.isNullOrEmpty()) {
            return "-"
        }
        return list.joinToString(separator = ", ")
    }

}