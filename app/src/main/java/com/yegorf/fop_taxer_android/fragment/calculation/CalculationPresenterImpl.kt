package com.yegorf.fop_taxer_android.fragment.calculation

import com.yegorf.fop_taxer_android.presentation.AbstractPresenter
import com.yegorf.fop_taxer_android.tools.DateHelper
import okhttp3.*
import org.json.JSONArray
import java.io.IOException

class CalculationPresenterImpl : AbstractPresenter<CalculationView>(), CalculationPresenter {

    private var okHttpClient = OkHttpClient()

    override fun getCurrency() {
        val currentDate = DateHelper.getCurrentDate("yyyyMMdd")
        val url =
            "https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?valcode=USD&date=$currentDate&json"
        val request = Request.Builder()
            .url(url)
            .build()

        okHttpClient.newCall(request)
            .enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    runOnUiThread {
                        view?.setCurrency(-1f)
                    }
                }

                override fun onResponse(call: Call, response: Response) {
                    response.body
                        ?.string()
                        ?.let {
                            val rate = JSONArray(it).getJSONObject(0).get("rate")
                            runOnUiThread {
                                view?.setCurrency(rate.toString().toFloat())
                            }
                        }
                }
            })
    }
}