package com.yegorf.fop_taxer_android.fragment

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.yegorf.fop_taxer_android.Constants
import com.yegorf.fop_taxer_android.R
import com.yegorf.fop_taxer_android.databinding.FragmentCaltulationBinding
import okhttp3.*
import org.json.JSONArray
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class CalculationFragment : Fragment() {

    private lateinit var binding: FragmentCaltulationBinding
    private var okHttpClient = OkHttpClient()
    private var usdRate: Float = -1f

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCaltulationBinding.inflate(inflater)
        setOnClickListeners();
        getCurrency()
        return binding.root;
    }

    private fun setOnClickListeners() {
        binding.btnCalculate.setOnClickListener {
            val totalIncomeText = binding.etIncomeTotal.text
            val totalIncome: Float = totalIncomeText.toString().toFloat()
            val tax = calculateTax(totalIncome)
            val pureIncome = totalIncome - tax
            val stringTax = "%.2f".format(tax)
            val stringPureIncome = "%.2f".format(pureIncome)

            binding.resultsContainer.visibility = View.VISIBLE
            binding.tvTaxResult.setResult(stringTax)
            binding.tvPureResult.setResult(stringPureIncome)

            if (usdRate != -1f) {
                val taxUsd = tax / usdRate
                val pureIncomeUsd = pureIncome / usdRate
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
        if (activity != null) {
            val clipboard =
                activity!!.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("label", text)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(context, getString(R.string.copied_to_clipboard), Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun calculateTax(totalIncome: Float) = totalIncome * Constants.TAX_EN_3

    private fun getCurrency() {
        val currentDate = SimpleDateFormat("yyyyMMdd", Locale.US).format(Date())
        val url =
            "https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?valcode=USD&date=$currentDate&json"
        val request = Request.Builder().url(url).build()

        okHttpClient.newCall(request)
            .enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    activity?.runOnUiThread {
                        binding.tvCurrency.text = getString(R.string.get_rates_fail)
                        binding.ratesProgressBar.visibility = View.INVISIBLE
                    }
                }

                override fun onResponse(call: Call, response: Response) {
                    val json = response.body()?.string()
                    if (json != null) {
                        val rate = JSONArray(json).getJSONObject(0).get("rate")
                        usdRate = rate.toString().toFloat()
                        activity?.runOnUiThread {
                            binding.tvCurrency.text =
                                getString(R.string.usd_rate_template, rate.toString())
                            binding.ratesProgressBar.visibility = View.INVISIBLE
                        }
                    }
                }
            })
    }
}