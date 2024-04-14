package ru.bashcony.kinosearch.presentation.search

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import ru.bashcony.kinosearch.databinding.DialogGenresPickerBinding
import ru.bashcony.kinosearch.domain.movie.entity.ValueEntity
import ru.bashcony.kinosearch.presentation.search.adapter.GenresAdapter

class GenresPickerDialogFragment : DialogFragment() {

    private lateinit var binding: DialogGenresPickerBinding
    private val searchViewModel: SearchViewModel by activityViewModels()
    private val tempSelectedGenres = arrayListOf<ValueEntity>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = DialogGenresPickerBinding.inflate(inflater, container, false).let {
        binding = it
        it.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val adapter = GenresAdapter(onGenreCheckChanged = { genre, isChecked ->
            if (isChecked)
                tempSelectedGenres.add(genre)
            else
                tempSelectedGenres.remove(genre)
        }).apply {
            setHasStableIds(true)
        }

        binding.genreRecycler.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            setItemViewCacheSize(100)
            setAdapter(adapter)
        }

        searchViewModel.loadedGenres.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        binding.genresNoMatter.setOnClickListener {
            searchViewModel.cleanGenres()
            dismiss()
        }

        binding.genresContinue.setOnClickListener {
            searchViewModel.selectGenres(tempSelectedGenres)
            dismiss()
        }
    }
}