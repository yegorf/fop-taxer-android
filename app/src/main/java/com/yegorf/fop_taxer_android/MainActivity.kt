package com.yegorf.fop_taxer_android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yegorf.fop_taxer_android.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        FragmentNavigator(supportFragmentManager).openFragment(FragmentNavigator.Screen.CALCULATION)
    }
}