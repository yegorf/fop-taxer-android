package com.yegorf.fop_taxer_android.presentation

interface Presenter<T> where T : View {

    fun onCreate(view: T)
}