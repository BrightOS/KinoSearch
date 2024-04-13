package ru.bashcony.kinosearch.presentation.movie

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.transition.MaterialSharedAxis
import dagger.hilt.android.AndroidEntryPoint
import ru.bashcony.kinosearch.databinding.FragmentMovieBinding
import ru.bashcony.kinosearch.infra.utils.DrawableAlwaysCrossFadeFactory
import ru.bashcony.kinosearch.infra.utils.registerDataObserver
import ru.bashcony.kinosearch.presentation.movie.adapter.GenresAdapter
import ru.bashcony.kinosearch.presentation.movie.adapter.ImagesAdapter
import ru.bashcony.kinosearch.presentation.movie.adapter.PersonsAdapter
import ru.bashcony.kinosearch.presentation.movie.adapter.PremieresAdapter
import ru.bashcony.kinosearch.presentation.movie.adapter.ReviewsAdapter
import ru.bashcony.kinosearch.presentation.movie.adapter.SeasonsAdapter
import ru.bashcony.kinosearch.presentation.movie.adapter.SimilarMoviesAdapter
import ru.bashcony.kinosearch.presentation.movie.adapter.VideosAdapter
import java.util.Locale


@AndroidEntryPoint
class MovieFragment : Fragment() {

    private lateinit var binding: FragmentMovieBinding
    private val movieViewModel: MovieViewModel by viewModels()
    private val args: MovieFragmentArgs by navArgs()

    private var internetErrorVisibility: Boolean
        get() = binding.internetErrorLayout.root.visibility == View.VISIBLE
        set(value) {
            binding.internetErrorLayout.root.visibility =
                if (value)
                    View.VISIBLE
                else
                    View.GONE
        }

    private var loadingVisibility: Boolean
        get() = binding.loadingLayout.root.visibility == View.VISIBLE
        set(value) {
            binding.loadingLayout.root.visibility =
                if (value)
                    View.VISIBLE
                else
                    View.GONE
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        exitTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
        reenterTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentMovieBinding.inflate(inflater, container, false).let {
        binding = it
        it.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.descriptionExpand.setOnClickListener {
            binding.description.maxLines.let {
                binding.description.maxLines = if (it == 5) 100 else 5
                binding.descriptionExpand.text =
                    if (it == 5) "Показать меньше" else "Показать больше"
            }
        }

        movieViewModel.state.observe(viewLifecycleOwner) {

            setupRecyclers()

            binding.backButton.setOnClickListener {
                findNavController().popBackStack()
            }

            Log.d("state", it::class.qualifiedName.toString())

            when (it) {
                is MovieFragmentState.Error -> {
                    // Show loading screen
                    it.error.printStackTrace()
                }

                is MovieFragmentState.ResponseError -> {
                    with(binding.internetErrorLayout) {
                        errorCode.text = "${it.code}"
                        errorMessage.text = it.message

                        errorActionButton.setOnClickListener {
                            movieViewModel.loadMovieInformation(
                                if (args.movieId == -1) 1312253 else args.movieId
                            )
                        }
                    }

                    loadingVisibility = false
                    internetErrorVisibility = true
                }

                MovieFragmentState.Init -> {
                    internetErrorVisibility = false
                    loadingVisibility = true

                    movieViewModel.loadMovieInformation(
                        if (args.movieId == -1) 1312253 else args.movieId
                    )
                }

                is MovieFragmentState.IsLoading -> {
                    internetErrorVisibility = false
                    loadingVisibility = it.isLoading
                }

                is MovieFragmentState.ShowToast -> {
                    Toast.makeText(
                        context,
                        it.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is MovieFragmentState.MovieLoaded -> {
                    internetErrorVisibility = false
                    loadingVisibility = false

                    with(it.movieEntity) {
                        if (name != null) {
                            binding.titleRussian.apply {
                                text = name
                                visibility = View.VISIBLE
                            }
                        } else
                            binding.titleRussian.visibility = View.GONE

                        if (alternativeName != null) {
                            binding.titleOriginal.apply {
                                text = alternativeName
                                visibility = View.VISIBLE
                            }
                        } else
                            binding.titleOriginal.visibility = View.GONE

                        binding.mainInformation.text =
                            if (isSeries == true)
                                "${seasonsInfo?.count()} сезона, ${seasonsInfo.orEmpty().sumOf { it.episodesCount ?: 0 }} серии по ~$seriesLength мин."
                            else
                                "Фильм, ~$movieLength мин."

                        binding.countryAndDate.text =
                            "${countries?.map { it.name }?.joinToString(", ")}, ${
                                releaseYears?.getOrNull(0)?.start.let {
                                    if (it != null) "с $it " else if (isSeries != true) "$year " else ""
                                }
                            }${
                                releaseYears?.getOrNull(0)?.end.let {
                                    if (it != null) "по $it " else ""
                                }
                            }${
                                if (releaseYears != null)
                                    "г."
                                else
                                    ""
                            }"


                        if (ratingMpaa != null) {
                            binding.mpaa.apply {
                                visibility = View.VISIBLE
                                text = "$ratingMpaa"
                            }
                        } else
                            binding.mpaa.visibility = View.GONE

                        if (ageRating != null) {
                            binding.ageRestriction.apply {
                                visibility = View.VISIBLE
                                text = "$ageRating+"
                            }
                        } else
                            binding.ageRestriction.visibility = View.GONE

                        rating?.imdb.let {
                            if (it != null) {
                                binding.imdbRating.text =
                                    String.format(Locale.ENGLISH, "%.1f", it)
                                binding.imdb.visibility = View.VISIBLE
                            } else binding.imdb.visibility = View.GONE
                        }

                        rating?.tmdb.let {
                            if (it != null) {
                                binding.tmdbRating.text =
                                    String.format(Locale.ENGLISH, "%.1f", it)
                                binding.tmdb.visibility = View.VISIBLE
                            } else
                                binding.tmdb.visibility = View.GONE
                        }

                        rating?.kp?.let {
                            binding.kpRating.text = String.format(Locale.ENGLISH, "%.1f", it)
                            binding.kp.visibility = View.VISIBLE
                        } ?: { binding.kp.visibility = View.GONE }

                        binding.description.text = description

                        binding.descriptionExpand.visibility = if (binding.description.lineCount >= 5)
                            View.VISIBLE
                        else
                            View.GONE

                        binding.genreRecycler.adapter?.let {
                            if (it is GenresAdapter)
                                it.submitList(genres?.map { it.name })
                        }

                        binding.premiereRecycler.adapter?.let {
                            if (it is PremieresAdapter)
                                it.submitList(
                                    arrayListOf<Pair<String, String>>().apply {
                                        premiere?.world?.let { add("В мире" to it) }
                                        premiere?.russia?.let { add("В России" to it) }
                                        premiere?.digital?.let { add("В цифре" to it) }
                                        premiere?.cinema?.let { add("В кино" to it) }
                                    }
                                )
                        }

                        binding.similarMoviesRecycler.adapter?.let {
                            if (it is SimilarMoviesAdapter)
                                it.submitList(similarMovies)
                        }

                        binding.sequelsAndPrequelsRecycler.adapter?.let {
                            if (it is SimilarMoviesAdapter)
                                it.submitList(sequelsAndPrequels)
                        }

                        binding.videoRecycler.adapter?.let {
                            if (it is VideosAdapter)
                                it.submitList(trailersEntity?.trailers.orEmpty())
                        }

                        Glide.with(binding.root.context)
                            .load(it.movieEntity.poster?.url)
                            .apply(RequestOptions().centerCrop()
                                .override(
                                    binding.frontCover.measuredWidth,
                                    binding.frontCover.measuredHeight
                                ))
                            .thumbnail(
                                Glide.with(binding.root.context)
                                    .load(it.movieEntity.poster?.previewUrl)
                                    .apply(RequestOptions().centerCrop())
                            )
                            .transition(DrawableTransitionOptions.with(DrawableAlwaysCrossFadeFactory()))
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(binding.frontCover)

                        Glide.with(binding.root.context)
                            .load(it.movieEntity.backdrop?.url ?: it.movieEntity.poster?.url)
                            .thumbnail(
                                Glide.with(binding.root.context)
                                    .load(it.movieEntity.backdrop?.previewUrl)
                                    .apply(RequestOptions().centerCrop())
                            )
                            .transition(
                                DrawableTransitionOptions.with(
                                    DrawableAlwaysCrossFadeFactory()
                                ))
                            .centerCrop()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(binding.backCover)
                    }
                }
            }
        }
    }

    private fun setupRecyclers() {
        binding.seasonsRecycler.apply {
            layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                true
            ).apply {
                stackFromEnd = true
            }
            emptyText = "сезонах и сериях"
            adapter = getSeasonsAdapter()
        }

        binding.genreRecycler.apply {
            layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            emptyText = "жанрах"
            adapter = getGenresAdapter()
        }

        binding.premiereRecycler.apply {
            layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            emptyText = "датах проката"
            adapter = getPremieresAdapter()
        }

        binding.similarMoviesRecycler.apply {
            layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            emptyText = "похожих фильмах"
            adapter = getMoviesListAdapter()
        }

        binding.sequelsAndPrequelsRecycler.apply {
            layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            emptyText = "сиквелах и приквелах"
            adapter = getMoviesListAdapter()
        }

        binding.videoRecycler.apply {
            layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            emptyText = "видео"
            adapter = getVideosAdapter()
        }

        binding.personRecycler.apply {
            layoutManager = GridLayoutManager(
                context,
                3,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            emptyText = "актёрах и съёмочной группе"
            adapter = getPersonsAdapter()
        }

        binding.reviewRecycler.apply {
            layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            emptyText = "рецензиях"
            adapter = getReviewsAdapter()
        }

        binding.imageRecycler.apply {
            layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            emptyText = "изображениях"
            adapter = getImagesAdapter()
        }
    }

    private fun getSeasonsAdapter() =
        SeasonsAdapter(onSeasonClick = {
            findNavController()
        }).apply {
            movieViewModel.doOnSeasonsLoaded {
                submitData(lifecycle, it)
            }
            registerDataObserver {

            }
        }

    private fun getGenresAdapter() =
        GenresAdapter(onGenreClick = {
            findNavController()
        })

    private fun getPremieresAdapter() =
        PremieresAdapter()

    private fun getMoviesListAdapter() =
        SimilarMoviesAdapter(onMovieClick = {
            findNavController().navigate(MovieFragmentDirections.actionMovieFragmentSelf(it))
        })

    private fun getVideosAdapter() =
        VideosAdapter(onVideoClick = {
            val i = Intent(Intent.ACTION_VIEW)
            i.setData(Uri.parse(it))
            startActivity(i)
        })

    private fun getPersonsAdapter() =
        PersonsAdapter(onPersonClick = {
            findNavController()
        }).apply {
            movieViewModel.doOnPersonsLoaded {
                submitData(lifecycle, it)
            }
        }

    private fun getReviewsAdapter() =
        ReviewsAdapter(onReviewClick = {
            findNavController()
        }).apply {
            movieViewModel.doOnReviewsLoaded {
                submitData(lifecycle, it)
            }
        }

    private fun getImagesAdapter() =
        ImagesAdapter(onImageClick = {
            findNavController()
        }).apply {
            movieViewModel.doOnImagesLoaded {
                submitData(lifecycle, it)
            }
        }
}