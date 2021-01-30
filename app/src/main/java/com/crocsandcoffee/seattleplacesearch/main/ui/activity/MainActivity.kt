package com.crocsandcoffee.seattleplacesearch.main.ui.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.asLiveData
import com.crocsandcoffee.seattleplacesearch.R
import com.crocsandcoffee.seattleplacesearch.SeattlePlaceSearchApplication
import com.crocsandcoffee.seattleplacesearch.databinding.MainActivityBinding
import com.crocsandcoffee.seattleplacesearch.main.ui.fragment.DetailsFragment
import com.crocsandcoffee.seattleplacesearch.main.ui.fragment.SearchFragment
import com.crocsandcoffee.seattleplacesearch.main.viewmodel.MainActivityViewModel
import com.crocsandcoffee.seattleplacesearch.main.viewmodel.NavigationEvent
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding

    @Inject
    lateinit var factory: MainActivityViewModel.Factory
    private val viewModel: MainActivityViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {

        (applicationContext as SeattlePlaceSearchApplication)
            .appComponent
            .inject(this)

        super.onCreate(savedInstanceState)

        binding = MainActivityBinding.inflate(layoutInflater)

        setContentView(binding.root)

        if (savedInstanceState == null) {
            replaceFragment(SearchFragment.newInstance())
        }

        subscribe()
    }

    private fun subscribe() {
        viewModel.event.asLiveData().observe(this) { event ->

            when (event) {
                is NavigationEvent.NavigateToDetails -> {
                    replaceFragment(DetailsFragment.newInstance(), addToBackStack = true, DetailsFragment.TAG)
                }
            }
        }
    }

    private fun replaceFragment(fragment: Fragment, addToBackStack: Boolean = false, tag: String? = null) {
        supportFragmentManager.commit {
            replace(R.id.container, fragment)
            if (addToBackStack) addToBackStack(tag)
            setReorderingAllowed(true)
        }
    }
}