package ru.bashcony.kinosearch.presentation.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.distinctUntilChanged
import dagger.hilt.android.AndroidEntryPoint
import ru.bashcony.kinosearch.databinding.FragmentMovieBinding

@AndroidEntryPoint
class MovieFragment : Fragment() {

    private lateinit var binding: FragmentMovieBinding
    private val movieViewModel: MovieViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentMovieBinding.inflate(inflater, container, false).let {
        binding = it
        it.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        movieViewModel.state.distinctUntilChanged().observe(viewLifecycleOwner) {
            when (it) {
                is MovieFragmentState.Error -> TODO()
                is MovieFragmentState.ErrorResponse -> TODO()
                MovieFragmentState.Init -> {}
                is MovieFragmentState.IsLoading -> TODO()
                is MovieFragmentState.ShowToast -> TODO()
                is MovieFragmentState.Success -> {
                    with(it.movieResponse) {
                        binding.titleRussian.text = name
                        binding.titleOriginal.text = alternativeName
                    }
                }
            }
        }

        movieViewModel.getMovieById(1312253)
    }
}