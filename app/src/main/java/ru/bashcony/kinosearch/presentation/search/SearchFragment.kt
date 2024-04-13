package ru.bashcony.kinosearch.presentation.search

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.badge.BadgeUtils
import com.google.android.material.badge.ExperimentalBadgeUtils
import dagger.hilt.android.AndroidEntryPoint
import ru.bashcony.kinosearch.R
import ru.bashcony.kinosearch.databinding.FragmentSearchBinding
import ru.bashcony.kinosearch.infra.utils.addOnKeyboardVisibilityListener
import ru.bashcony.kinosearch.infra.utils.registerDataObserver


@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private val searchViewModel: SearchViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentSearchBinding.inflate(inflater, container, false).let {
        binding = it
        it.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        searchViewModel.setupSearchMoviesPager()
        searchViewModel.setupSearchSubject()

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

        searchViewModel.loading.observe(viewLifecycleOwner) {
            binding.searchIndicator.visibility = if (it)
                View.VISIBLE
            else
                View.GONE
        }

        binding.searchText.doAfterTextChanged {
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
        }

        binding.searchText.setOnEditorActionListener { v, actionId, event ->
            if (EditorInfo.IME_ACTION_SEARCH == actionId) {
                searchViewModel.forceSubmitQuery(v.text.toString())
                hideKeyboard()
            }

            false
        }

        binding.searchText.addOnKeyboardVisibilityListener(
            onKeyboardShown = {
                binding.searchText.requestFocus()
            },
            onKeyboardHidden = {
                binding.searchText.clearFocus()
            }
        )

        binding.searchText.setCompoundDrawablesWithIntrinsicBounds(
            ContextCompat.getDrawable(binding.searchText.context, R.drawable.ic_filter),
            null,
            ContextCompat.getDrawable(binding.searchText.context, R.drawable.ic_search),
            null
        )
//
//        BadgeUtils.attachBadgeDrawable(
//            BadgeDrawable.create(binding.searchText.context).apply {
//                maxCharacterCount = 1
//                number = 1
//                badgeTextColor = Color.WHITE
//                backgroundColor =
//                    ContextCompat.getColor(binding.searchText.context, R.color.color_bad)
//                isVisible = true
//            },
//            binding.searchText
//        )
    }

    private fun hideKeyboard() {
        (activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
            .hideSoftInputFromWindow(activity?.currentFocus?.windowToken, 0)
    }

    private fun getMovieSearchAdapter() =
        MovieSearchAdapter(onMovieClick = {
            findNavController().navigate(
                SearchFragmentDirections.actionSearchFragmentToMovieFragment(it)
            )
        }).apply {
            searchViewModel.doOnMoviesLoaded {
                submitData(lifecycle, it)
            }
        }
}