package com.crocsandcoffee.seattleplacesearch.main.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.crocsandcoffee.seattleplacesearch.R
import com.crocsandcoffee.seattleplacesearch.main.ui.fragment.SearchFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, SearchFragment.newInstance())
                .commitNow()
        }
    }
}