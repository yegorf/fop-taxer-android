package com.yegorf.fop_taxer_android.presentation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

open class AbstractPresenter<T> : Presenter<T> where T : View {

    var view: T? = null

    override fun onCreate(view: T) {
        this.view = view
    }

    protected fun runOnUiThread(action: () -> Unit) {
        CoroutineScope(Dispatchers.Main).launch {
            action.invoke()
        }
    }
}