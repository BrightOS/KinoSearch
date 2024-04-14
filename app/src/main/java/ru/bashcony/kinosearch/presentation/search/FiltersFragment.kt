package ru.bashcony.kinosearch.presentation.search

import android.R
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListPopupWindow
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.bashcony.kinosearch.databinding.FragmentFiltersBinding
import ru.bashcony.kinosearch.domain.movie.entity.ValueEntity
import ru.bashcony.kinosearch.presentation.search.adapter.NoFilterAdapter

@AndroidEntryPoint
class FiltersFragment : Fragment() {

    private lateinit var binding: FragmentFiltersBinding
    private val searchViewModel: SearchViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentFiltersBinding.inflate(inflater, container, false).let {
        binding = it
        it.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        searchViewModel.selectedYears.observe(viewLifecycleOwner) {
            binding.yearFromText.setText(
                if (it.first > -1)
                    "${it.first} - ${it.second}"
                else
                    "Неважно"
            )
        }

        searchViewModel.selectedGenres.observe(viewLifecycleOwner) {
            binding.genresText.setText(
                if (it != null && it.isNotEmpty())
                    it.map { it.name }.joinToString(", ")
                else
                    "Неважно"
            )
        }

        searchViewModel.selectedCountries.observe(viewLifecycleOwner) {
            binding.countryText.setText(
                if (it != null && it.isNotEmpty())
                    it.map { it.name }.joinToString(", ").let {
                        if (it == "null")
                            "Неважно"
                        else
                            it
                    }
                else
                    "Неважно"
            )
        }

        binding.yearFromText.setOnClickListener {
            showYearsDialog()
        }

        binding.years.setOnClickListener {
            showYearsDialog()
        }

        binding.genresText.setOnClickListener {
            showGenresDialog()
        }

        binding.genres.setOnClickListener {
            showGenresDialog()
        }

        binding.cleanFilters.setOnClickListener {
            searchViewModel.cleanFilters()
        }

        binding.backButton.setOnClickListener {
            searchViewModel.switchFiltersVisibility()
        }

        searchViewModel.loadedCountries.observe(viewLifecycleOwner) {
            binding.countryText.setAdapter(
                NoFilterAdapter(
                    binding.countryText.context,
                    R.layout.simple_dropdown_item_1line,
                    it.plus(ValueEntity("Неважно", null)).map { it.name }
                )
            )
        }

        val ageList = listOf("G", "PG", "PG-13", "R", "R-17", "Неважно")

        binding.ageRestrictionText.setAdapter(
            NoFilterAdapter(
                binding.countryText.context,
                R.layout.simple_dropdown_item_1line,
                ageList
            )
        )

        binding.ageRestrictionText.setOnItemClickListener { parent, view, position, id ->
            if (position == ageList.size - 1)
                searchViewModel.cleanAge()
            else
                searchViewModel.selectAge(
                    ageList[position]
                )
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            binding.countryText.inputMethodMode = ListPopupWindow.INPUT_METHOD_NOT_NEEDED
        }

//        searchViewModel.loadedGenres.observe(viewLifecycleOwner) {
//            binding.genresText.setAdapter(
//                ArrayAdapter(
//                    binding.genresText.context,
//                    android.R.layout.simple_dropdown_item_1line,
//                    it.plus(ValueEntity("Неважно", null)).map { it.name }
//                )
//            )
//        }

//        binding.genresText.setOnItemClickListener { _, _, position, _ ->
//            if (position > (searchViewModel.loadedGenres.value?.size ?: 0))
//                searchViewModel.cleanGenres()
//            else
//                searchViewModel.selectGenres(
//                    listOf(
//                        searchViewModel.loadedGenres.value.orEmpty().getOrNull(position)
//                            ?: ValueEntity(null, null)
//                    )
//                )
//        }

        binding.countryText.setOnItemClickListener { _, _, position, _ ->
            if (position > (searchViewModel.loadedCountries.value?.size ?: 0))
                searchViewModel.cleanCountries()
            else
                searchViewModel.selectCountries(
                    listOf(
                        searchViewModel.loadedCountries.value.orEmpty().getOrNull(position)
                            ?: ValueEntity(null, null)
                    )
                )
        }

        binding.filtersInnerRoot.setOnClickListener {
            searchViewModel.switchFiltersVisibility()
        }

        binding.filtersScroll.setOnClickListener {
            searchViewModel.switchFiltersVisibility()
        }

        binding.filtersRoot.setOnClickListener {
            searchViewModel.switchFiltersVisibility()
        }

    }

    private fun showYearsDialog() {
        YearsPickerDialogFragment().show(childFragmentManager, "years")
    }

    private fun showGenresDialog() {
        GenresPickerDialogFragment().show(childFragmentManager, "genres")
    }

}