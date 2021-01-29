package com.crocsandcoffee.seattleplacesearch.main.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commitNow
import com.crocsandcoffee.seattleplacesearch.R
import com.crocsandcoffee.seattleplacesearch.databinding.MainActivityBinding
import com.crocsandcoffee.seattleplacesearch.main.ui.fragment.SearchFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = MainActivityBinding.inflate(layoutInflater)

        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager
                .commitNow {
                    replace(R.id.container, SearchFragment.newInstance())
                    setReorderingAllowed(true)
                }
        }
    }
}