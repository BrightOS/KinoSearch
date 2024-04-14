package ru.bashcony.kinosearch.presentation.search

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import ru.bashcony.kinosearch.databinding.DialogYearsPickerBinding

class YearsPickerDialogFragment : DialogFragment() {

    private lateinit var binding: DialogYearsPickerBinding
    private val searchViewModel: SearchViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = DialogYearsPickerBinding.inflate(inflater, container, false).let {
        binding = it
        it.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.fromYear.apply {
            setFormatter {
                return@setFormatter if (it == 1899)
                    "с"
                else
                    "$it"
            }
            minValue = 1899
            maxValue = 2024
            value = searchViewModel.selectedYears.value?.first.let {
                if (it != null && it != -1)
                    it
                else
                    1899
            }
            setOnValueChangedListener { picker, oldVal, newVal ->
                binding.toYear.minValue = newVal
            }
        }

        binding.toYear.apply {
            setFormatter {
                return@setFormatter if (it == 2025)
                    "по"
                else
                    "$it"
            }
            minValue = 1900
            maxValue = 2025
            value = searchViewModel.selectedYears.value?.second.let {
                if (it != null && it != -1)
                    it
                else
                    2025
            }
            setOnValueChangedListener { picker, oldVal, newVal ->
                binding.fromYear.maxValue = newVal
            }
        }

        binding.yearsContinue.setOnClickListener {
            searchViewModel.selectYears(binding.fromYear.value, binding.toYear.value)
            dismiss()
        }

        binding.yearsNoMatter.setOnClickListener {
            searchViewModel.cleanYears()
            dismiss()
        }
    }
}