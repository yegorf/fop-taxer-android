package com.yegorf.fop_taxer_android.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.yegorf.fop_taxer_android.R
import com.yegorf.fop_taxer_android.databinding.ResultTextViewBinding

class ResultTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    private val binding = ResultTextViewBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.ResultTextView, 0, 0)
        val description = attributes.getString(R.styleable.ResultTextView_descriptionText)
        binding.tvResultDescription.text = description
        attributes.recycle()
    }

    fun setResult(result: String) {
        binding.tvResultSum.text = result
    }

    fun setResultUSD(result: String) {
        binding.tvResultSumUsd.text = context.getString(R.string.usd_result_template, result)
    }

    fun setOnCopyClickListener(listener: (View) -> Unit) {
        binding.ivCopy.setOnClickListener(listener)
    }
}