package com.yegorf.fop_taxer_android.fragment.calculation

import com.yegorf.fop_taxer_android.presentation.Presenter

interface CalculationPresenter : Presenter<CalculationView> {

    fun getCurrency()
}