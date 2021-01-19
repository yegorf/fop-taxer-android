package com.yegorf.fop_taxer_android.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.yegorf.fop_taxer_android.Constants
import com.yegorf.fop_taxer_android.databinding.FragmentCaltulationBinding

class CalculationFragment : Fragment() {

    private lateinit var binding: FragmentCaltulationBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCaltulationBinding.inflate(inflater)
        setOnClickListeners();
        return binding.root;
    }

    private fun setOnClickListeners() {
        binding.btnCalculate.setOnClickListener {
            val totalIncomeText = binding.etIncomeTotal.text
            val totalIncome: Float = totalIncomeText.toString().toFloat()
            binding.tvTaxSum.text = calculateTax(totalIncome).toString()
        }
    }

    private fun calculateTax(totalIncome: Float) = totalIncome * Constants.TAX_EN_3
}