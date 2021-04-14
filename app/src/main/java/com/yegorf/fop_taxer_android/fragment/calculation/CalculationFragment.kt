package com.yegorf.fop_taxer_android.fragment.calculation

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.yegorf.fop_taxer_android.R
import com.yegorf.fop_taxer_android.databinding.FragmentCaltulationBinding


class CalculationFragment : Fragment(), CalculationView {

    private lateinit var binding: FragmentCaltulationBinding
    private val presenter: CalculationPresenter = CalculationPresenterImpl()
    private var usdRate: Float = -1f

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCaltulationBinding.inflate(inflater)
        presenter.onCreate(this)
        presenter.getCurrency()
        setOnClickListeners()
        return binding.root
    }

    private fun setOnClickListeners() {
        binding.etIncomeTotal.addTextChangedListener {
            binding.incomeTotalLayout.isErrorEnabled = false
        }
        binding.etTaxPercent.addTextChangedListener {
            binding.taxPercentLayout.isErrorEnabled = false
        }

        binding.btnCalculate.setOnClickListener {
            var isIncomeValidated = true
            var isPercentValidated = true

            val totalIncomeText = binding.etIncomeTotal.text.toString()
            val taxText = binding.etTaxPercent.text.toString()

            if (totalIncomeText.isEmpty()) {
                if (isIncomeValidated) {
                    binding.incomeTotalLayout.error = getString(R.string.validation_error_empty)
                    isIncomeValidated = false
                }
            }
            if (taxText.isEmpty()) {
                if (isPercentValidated) {
                    binding.taxPercentLayout.error = getString(R.string.validation_error_empty)
                    isPercentValidated = false
                }
            }

            if (!isIncomeValidated && !isPercentValidated) {
                return@setOnClickListener
            }

            var totalIncome = 0f
            var tax = 0f

            try {
                totalIncome = totalIncomeText.toFloat()
            } catch (e: NumberFormatException) {
                if (isIncomeValidated) {
                    binding.incomeTotalLayout.error = getString(R.string.validation_error_format)
                    isIncomeValidated = false
                }
            }

            try {
                tax = taxText.toFloat()
            } catch (e: NumberFormatException) {
                if (isPercentValidated) {
                    binding.taxPercentLayout.error = getString(R.string.validation_error_format)
                    isPercentValidated = false
                }
            }

            if (!isIncomeValidated && !isPercentValidated) {
                return@setOnClickListener
            }

            if (totalIncome == 0f) {
                if (isIncomeValidated) {
                    binding.incomeTotalLayout.error = getString(R.string.validation_error_zero)
                    isIncomeValidated = false
                }
            }
            if (tax == 0f) {
                if (isPercentValidated) {
                    binding.taxPercentLayout.error = getString(R.string.validation_error_zero)
                    isPercentValidated = false
                }
            }

            if (!isIncomeValidated || !isPercentValidated) {
                return@setOnClickListener
            }

            val taxSum = totalIncome * tax / 100
            val pureIncomeSum = totalIncome - taxSum

            val stringTax = "%.2f".format(taxSum)
            val stringPureIncome = "%.2f".format(pureIncomeSum)

            binding.resultsContainer.visibility = View.VISIBLE
            binding.tvTaxResult.setResult(stringTax)
            binding.tvPureResult.setResult(stringPureIncome)

            if (usdRate != -1f) {
                val taxUsd = taxSum / usdRate
                val pureIncomeUsd = pureIncomeSum / usdRate
                val stringTaxUsd = "%.2f".format(taxUsd)
                val stringPureIncomeUsd = "%.2f".format(pureIncomeUsd)
                binding.tvTaxResult.setResultUSD(stringTaxUsd)
                binding.tvPureResult.setResultUSD(stringPureIncomeUsd)
            }

            binding.tvTaxResult.setOnCopyClickListener {
                copyToClipboard(stringTax)
            }
            binding.tvPureResult.setOnCopyClickListener {
                copyToClipboard(stringPureIncome)
            }
        }
    }

    private fun copyToClipboard(text: String) {
        activity?.let {
            val clipboard =
                it.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("label", text)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(context, getString(R.string.copied_to_clipboard), Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun setCurrency(currency: Float) {
        if (currency != -1f) {
            usdRate = currency
            binding.tvCurrency.text = getString(R.string.usd_rate_template, currency.toString())
            binding.ratesProgressBar.visibility = View.INVISIBLE
        } else {
            binding.tvCurrency.text = getString(R.string.get_rates_fail)
            binding.ratesProgressBar.visibility = View.INVISIBLE
        }
    }
}