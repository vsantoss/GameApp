package com.vsanto.gameapp.ui.gamedetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.activity.addCallback
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
import com.vsanto.gameapp.domain.model.GameSummary
import com.vsanto.gameapp.domain.model.Image
import com.vsanto.gameapp.domain.model.InvolvedCompany
import com.vsanto.gameapp.domain.model.SimilarGame
import com.vsanto.gameapp.domain.model.UserGameState
import com.vsanto.gameapp.domain.model.Website
import com.vsanto.gameapp.ui.common.adapters.HorizontalGameListAdapter
import com.vsanto.gameapp.ui.gamedetail.adapters.CompanyAdapter
import com.vsanto.gameapp.ui.gamedetail.adapters.ScreenshotAdapter
import com.vsanto.gameapp.ui.common.adapters.WebsiteAdapter
import com.vsanto.gameapp.ui.gamedetail.dialogs.ExpandedImageDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat

@AndroidEntryPoint
class GameDetailFragment : Fragment() {

    private var _binding: FragmentGameDetailBinding? = null
    private val binding get() = _binding!!
    private val args: GameDetailFragmentArgs by navArgs()
    private val gameDetailViewModel: GameDetailViewModel by viewModels()

    private var gameId: Int = -1

    private var isWantSelected: Boolean = false
    private var isPlayingSelected: Boolean = false
    private var isPlayedSelected: Boolean = false

    private lateinit var screenshotAdapter: ScreenshotAdapter
    private lateinit var companyAdapter: CompanyAdapter
    private lateinit var similarGameAdapter: HorizontalGameListAdapter
    private lateinit var websiteAdapter: WebsiteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            navigateUp()
        }

        gameId = args.id

        gameDetailViewModel.getGame(gameId)
        initUI()
    }

    private fun initUI() {
        initListeners()
        initUIState()
    }

    private fun initListeners() {
        binding.fabBack.setOnClickListener { navigateUp() }

        val bounceAnimation: Animation =
            AnimationUtils.loadAnimation(requireContext(), R.anim.bounce)

        binding.llWant.setOnClickListener {
            isWantSelected = !isWantSelected
            isPlayingSelected = false
            isPlayedSelected = false
            setStatesColor()

            var state = UserGameState.UNSELECTED
            if (isWantSelected) {
                binding.ivWant.startAnimation(bounceAnimation)
                state = UserGameState.WANT
            }
            gameDetailViewModel.addGame(gameId, state)
        }

        binding.llPlaying.setOnClickListener {
            isWantSelected = false
            isPlayingSelected = !isPlayingSelected
            isPlayedSelected = false
            setStatesColor()

            var state = UserGameState.UNSELECTED
            if (isPlayingSelected) {
                binding.ivPlaying.startAnimation(bounceAnimation)
                state = UserGameState.PLAYING
            }
            gameDetailViewModel.addGame(gameId, state)
        }
        binding.llPlayed.setOnClickListener {
            isWantSelected = false
            isPlayingSelected = false
            isPlayedSelected = !isPlayedSelected
            setStatesColor()

            var state = UserGameState.UNSELECTED
            if (isPlayedSelected) {
                binding.ivPlayed.startAnimation(bounceAnimation)
                state = UserGameState.PLAYED
            }
            gameDetailViewModel.addGame(gameId, state)
        }

        binding.llList.setOnClickListener {
            navigateToAddToList()
        }

        binding.llSummary.setOnClickListener {
            binding.llSummary.isSelected = !binding.llSummary.isSelected
            if (binding.llSummary.isSelected) {
                binding.tvSummary.maxLines =
                    context?.resources?.getInteger(R.integer.summary_max_lines_selected) ?: 100
            } else {
                binding.tvSummary.maxLines =
                    context?.resources?.getInteger(R.integer.summary_max_lines) ?: 4
            }
        }
    }

    private fun navigateUp() {
        findNavController().navigateUp()
    }

    private fun navigateToAddToList() {
        findNavController().navigate(
            GameDetailFragmentDirections.actionGameDetailFragmentToAddToListFragment(gameId)
        )
    }

    private fun initUIState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                gameDetailViewModel.state.collect {
                    when (it) {
                        GameDetailState.Loading -> loadingState()
                        is GameDetailState.Error -> errorState()
                        is GameDetailState.Success -> successState(it)
                    }
                }
            }
        }
    }

    private fun setStatesColor() {
        binding.ivWant.setColorFilter(getStateColor(isWantSelected))
        binding.tvWant.setTextColor(getStateColor(isWantSelected))

        binding.ivPlaying.setColorFilter(getStateColor(isPlayingSelected))
        binding.tvPlaying.setTextColor(getStateColor(isPlayingSelected))

        binding.ivPlayed.setColorFilter(getStateColor(isPlayedSelected))
        binding.tvPlayed.setTextColor(getStateColor(isPlayedSelected))
    }

    private fun getStateColor(isSelected: Boolean): Int {
        val color = if (isSelected) {
            R.color.accent
        } else {
            R.color.white
        }
        return ContextCompat.getColor(requireContext(), color)
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

        loadTopImage(game)
        initLogo(game)

        binding.tvName.text = game.name

        val developer: InvolvedCompany? = getDeveloperCompany(game)
        binding.tvDeveloper.text = developer?.name.orEmpty()

        if (game.releaseDate != null) {
            val formatter = SimpleDateFormat("dd MMMM yyyy")
            binding.tvReleaseDate.text = formatter.format(game.releaseDate)
        }

        initRating(game.rating)

        initGameState(game.userGame?.state)

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

    private fun loadTopImage(game: GameDetail) {
        val topImage = selectTopImage(game)
        if (topImage != null) {
            Picasso.get().isLoggingEnabled = true
            Picasso.get().load(topImage.url).into(binding.ivTopImage)
        }
    }

    private fun selectTopImage(game: GameDetail): Image? {
        if (!game.artworks.isNullOrEmpty()) {
            return game.artworks[0]
        } else if (!game.screenshots.isNullOrEmpty()) {
            return game.screenshots.reversed()[0]
        }

        return null
    }

    private fun initLogo(game: GameDetail) {
        if (game.cover != null) {
            Picasso.get().isLoggingEnabled = true
            Picasso.get().load(game.cover.url).into(binding.ivLogo)
        }

        binding.ivLogo.setOnClickListener { openExpandedImage(game.cover?.url.orEmpty()) }
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

    private fun initGameState(state: UserGameState?) {
        if (state != null) {
            when (state) {
                UserGameState.UNSELECTED -> {}

                UserGameState.WANT -> {
                    isWantSelected = true
                }

                UserGameState.PLAYING -> {
                    isPlayingSelected = true
                }

                UserGameState.PLAYED -> {
                    isPlayedSelected = true
                }
            }

            setStatesColor()
        }
    }

    private fun initScreenshots(screenshots: List<Image>?) {
        screenshotAdapter = ScreenshotAdapter(screenshots.orEmpty()) { openExpandedImage(it) }
        binding.rvScreenshots.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = screenshotAdapter
        }
    }

    private fun initInvolvedCompanies(involvedCompanies: List<InvolvedCompany>?) {
        val sortedCompanies = involvedCompanies.orEmpty().sortedWith(
            compareBy({ it.developer },
                { it.publisher },
                { it.supporting },
                { it.porting })
        ).asReversed()

        companyAdapter = CompanyAdapter(sortedCompanies) { navigateToCompany(it) }
        binding.rvInvolvedCompanies.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = companyAdapter
        }
    }

    private fun initSimilarGames(similarGames: List<SimilarGame>?) {
        similarGameAdapter =
            HorizontalGameListAdapter(similarGames?.map { toGameSummary(it) }
                .orEmpty()) { navigateToOtherGame(it) }
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

    private fun openExpandedImage(src: String) {
        ExpandedImageDialogFragment(src)
            .show(parentFragmentManager, "Expanded Image Dialog")
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

    private fun toString(list: List<String>?): String {
        if (list.isNullOrEmpty()) {
            return "-"
        }
        return list.joinToString(separator = ", ")
    }

    private fun toGameSummary(similarGame: SimilarGame): GameSummary {
        return GameSummary(
            id = similarGame.id,
            name = similarGame.name,
            releaseDate = null,
            cover = similarGame.cover
        )
    }

}