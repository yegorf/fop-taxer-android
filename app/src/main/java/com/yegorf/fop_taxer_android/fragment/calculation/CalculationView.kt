package com.yegorf.fop_taxer_android.fragment.calculation

import com.yegorf.fop_taxer_android.presentation.View

interface CalculationView: View {

    fun setCurrency(currency: Float)
}