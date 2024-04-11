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
import com.bumptech.glide.request.RequestOptions
import dagger.hilt.android.AndroidEntryPoint
import ru.bashcony.kinosearch.databinding.FragmentMovieBinding
import ru.bashcony.kinosearch.presentation.movie.adapter.GenresAdapter
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

        binding.seasonsRecycler.apply {
            layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                true
            ).apply {
                stackFromEnd = true
            }
            adapter = getSeasonsAdapter()
        }

        binding.genreRecycler.apply {
            layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            adapter = getGenresAdapter()
        }

        binding.premiereRecycler.apply {
            layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            adapter = getPremieresAdapter()
        }

        binding.similarMoviesRecycler.apply {
            layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            adapter = getMoviesListAdapter()
        }

        binding.sequelsAndPrequelsRecycler.apply {
            layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            adapter = getMoviesListAdapter()
        }

        binding.videoRecycler.apply {
            layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            adapter = getVideosAdapter()
        }

        binding.personRecycler.apply {
            layoutManager = GridLayoutManager(
                context,
                3,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            adapter = getPersonsAdapter()
        }

        binding.reviewRecycler.apply {
            layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            adapter = getReviewsAdapter()
        }

        binding.descriptionExpand.setOnClickListener {
            binding.description.maxLines.let {
                binding.description.maxLines = if (it == 5) 100 else 5
                binding.descriptionExpand.text =
                    if (it == 5) "Показать меньше" else "Показать больше"
            }
        }

        movieViewModel.state.observe(viewLifecycleOwner) {

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

                is MovieFragmentState.SeasonsLoaded -> {

                }

                is MovieFragmentState.MovieLoaded -> {
                    internetErrorVisibility = false
                    loadingVisibility = false

                    with(it.movieResponse) {
                        binding.titleRussian.text = name
                        binding.titleOriginal.text = alternativeName

                        binding.mainInformation.text =
                            "${seasonsInfo?.count()} сезона, ${seasonsInfo?.sumOf { it.episodesCount }} серии по ~$seriesLength мин."

                        binding.countryAndDate.text =
                            "${countries?.map { it.name }?.joinToString(", ")}, ${
                                releaseYears?.get(0)?.start.let {
                                    if (it != null) "с $it " else ""
                                }
                            }${
                                releaseYears?.get(0)?.end.let {
                                    if (it != null) "по $it " else ""
                                }
                            }г."

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
                                it.submitList(trailersResponse?.trailers.orEmpty())
                        }

                        Glide.with(binding.root.context)
                            .load(it.movieResponse.poster?.url)
                            .apply(RequestOptions().centerCrop())
                            .thumbnail(
                                Glide.with(binding.root.context)
                                    .load(it.movieResponse.poster?.previewUrl)
                                    .apply(RequestOptions().centerCrop())
                            )
                            .into(binding.frontCover)

                        Glide.with(binding.root.context)
                            .load(it.movieResponse.backdrop?.url ?: it.movieResponse.poster?.url)
                            .apply(RequestOptions().centerCrop())
                            .thumbnail(
                                Glide.with(binding.root.context)
                                    .load(it.movieResponse.backdrop?.previewUrl)
                                    .apply(RequestOptions().centerCrop())
                            )
                            .into(binding.backCover)
                    }
                }
            }
        }
    }

    fun getSeasonsAdapter() =
        SeasonsAdapter(onSeasonClick = {
            findNavController()
        }).apply {
            movieViewModel.doOnSeasonsLoaded {
                submitData(lifecycle, it)
            }
        }

    fun getGenresAdapter() =
        GenresAdapter(onGenreClick = {
            findNavController()
        })

    fun getPremieresAdapter() =
        PremieresAdapter()

    fun getMoviesListAdapter() =
        SimilarMoviesAdapter(onMovieClick = {
            findNavController().navigate(MovieFragmentDirections.actionMovieFragmentSelf(it))
        })

    fun getVideosAdapter() =
        VideosAdapter(onVideoClick = {
            val i = Intent(Intent.ACTION_VIEW)
            i.setData(Uri.parse(it))
            startActivity(i)
        })

    fun getPersonsAdapter() =
        PersonsAdapter(onPersonClick = {
            findNavController()
        }).apply {
            movieViewModel.doOnPersonsLoaded {
                submitData(lifecycle, it)
            }
        }

    fun getReviewsAdapter() =
        ReviewsAdapter(onReviewClick = {
            findNavController()
        }).apply {
            movieViewModel.doOnReviewsLoaded {
                submitData(lifecycle, it)
            }
        }
}