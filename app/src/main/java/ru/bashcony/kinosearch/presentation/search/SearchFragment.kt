package ru.bashcony.kinosearch.presentation.search

import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionManager
import com.google.android.material.transition.MaterialSharedAxis
import dagger.hilt.android.AndroidEntryPoint
import ru.bashcony.kinosearch.R
import ru.bashcony.kinosearch.databinding.FragmentSearchBinding
import ru.bashcony.kinosearch.infra.utils.addOnKeyboardVisibilityListener
import ru.bashcony.kinosearch.infra.utils.getColorFromAttr
import ru.bashcony.kinosearch.infra.utils.registerDataObserver
import ru.bashcony.kinosearch.presentation.search.adapter.HistoryAdapter
import ru.bashcony.kinosearch.presentation.search.adapter.MovieSearchAdapter


@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var filtersFragment: FiltersFragment
    private val searchViewModel: SearchViewModel by activityViewModels()

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        exitTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
//        enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
//        reenterTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)
//        returnTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        searchViewModel.setupEverything()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentSearchBinding.inflate(inflater, container, false).let {
        binding = it
        filtersFragment = FiltersFragment()
        it.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        searchViewModel.loading.observe(viewLifecycleOwner) {
//            binding.searchIndicator.visibility = if (it)
//                View.VISIBLE
//            else
//                View.GONE
        }

        binding.movieSearchRecycler.apply {
            layoutManager = GridLayoutManager(
                context,
                3,
                GridLayoutManager.VERTICAL,
                false
            )

            adapter = getMovieSearchAdapter().apply {
                registerDataObserver {
                    searchViewModel.stopLoading()
                    if (binding.movieSearchRecycler.emptyText.isBlank())
                        emptyText = "фильмах и сериалах по такому запросу"
                }
            }
        }


        binding.searchText.doAfterTextChanged {
            if (binding.searchText.isEnabled)
                searchViewModel.sumbitQuery(it.toString())
        }

        binding.searchText.setOnFocusChangeListener { v, event ->
            binding.searchText.gravity = if (v.hasFocus())
                Gravity.START or Gravity.CENTER_VERTICAL
            else
                Gravity.CENTER

            binding.searchText.backgroundTintList = ColorStateList.valueOf(
                ContextCompat.getColor(
                    binding.root.context,
                    if (v.hasFocus())
                        R.color.background_search_text_color
                    else
                        R.color.background_search_text_color_alpha
                )
            )

            binding.searchBackground.backgroundTintList = ColorStateList.valueOf(
                ContextCompat.getColor(
                    binding.root.context,
                    if (v.hasFocus())
                        R.color.background_search_color
                    else
                        R.color.background_search_color_alpha
                )
            )

            binding.historyLayout.root.visibility =
                if (v.hasFocus())
                    View.VISIBLE
                else
                    View.GONE
        }

        binding.searchText.setOnEditorActionListener { v, actionId, event ->
            if (EditorInfo.IME_ACTION_SEARCH == actionId) {
                searchViewModel.forceSubmitQuery(v.text.toString())
                hideKeyboard()
            }

            false
        }

        binding.searchButton.setOnClickListener {
            searchViewModel.forceSubmitQuery(binding.searchText.text.toString())
            hideKeyboard()
        }

        binding.searchText.addOnKeyboardVisibilityListener(
            onKeyboardShown = {
                if (binding.searchText.isEnabled)
                    binding.searchText.requestFocus()
            },
            onKeyboardHidden = {
                if (binding.searchText.isEnabled)
                    binding.searchText.clearFocus()
            }
        )

        searchViewModel.filteredSearchEnabled.observe(viewLifecycleOwner) {
            binding.searchText.apply {
                if (it == true) {
                    isEnabled = false
                    isClickable = true
                    setText("Режим поиска по фильтрам")
                    setTextColor(context.getColorFromAttr(com.google.android.material.R.attr.colorPrimary))
                } else {
                    isEnabled = true
                    isClickable = false
                    if (text?.contains("Режим поиска по фильтрам") == true)
                        setText("")
                    setTextColor(context.getColorFromAttr(com.google.android.material.R.attr.colorOnSurface))
                }
            }

            binding.searchButton.isClickable = it == false
            binding.searchButton.visibility = if (it == true)
                View.INVISIBLE
            else
                View.VISIBLE

            if (it == true)
                binding.searchText.setOnClickListener {
                    searchViewModel.switchFiltersVisibility()
                }
            else
                binding.searchText.setOnClickListener(null)
        }

        searchViewModel.showFilters.observe(viewLifecycleOwner) {
            TransitionManager.beginDelayedTransition(
                binding.root,
                MaterialSharedAxis(
                    MaterialSharedAxis.Y,
                    it == true
                )
            )

            binding.filtersContainer.visibility =
                if (it == false)
                    View.GONE
                else
                    View.VISIBLE
        }

        binding.filterButton.setOnClickListener {
            searchViewModel.switchFiltersVisibility()
        }

        searchViewModel.searchHistory.observe(viewLifecycleOwner) {
            binding.historyLayout.root.apply {
                layoutManager = LinearLayoutManager(
                    context,
                    LinearLayoutManager.VERTICAL,
                    false
                )
                emptyText = "истории поиска"
                adapter = getHistoryAdapter()
            }
        }

    }

    private fun hideKeyboard() {
        (activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
            .hideSoftInputFromWindow(activity?.currentFocus?.windowToken, 0)
    }

    private fun getHistoryAdapter() =
        HistoryAdapter(
            onMovieClick = {
                findNavController().navigate(
                    SearchFragmentDirections.actionSearchFragmentToMovieFragment(it.id ?: 1)
                )
            },
            onDeleteClick = {
                searchViewModel.removeMovieFromHistoryDb(it)
            }
        )

    private fun getMovieSearchAdapter() =
        MovieSearchAdapter(onMovieClick = {
            searchViewModel.addMovieToHistoryDb(it)
            findNavController().navigate(
                SearchFragmentDirections.actionSearchFragmentToMovieFragment(it.id ?: -1)
            )
        }).apply {
            stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
            searchViewModel.doOnMoviesLoaded {
                submitData(lifecycle, it)
            }
        }
}