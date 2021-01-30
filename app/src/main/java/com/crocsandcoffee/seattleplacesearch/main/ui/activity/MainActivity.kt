package com.crocsandcoffee.seattleplacesearch.main.ui.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.asLiveData
import com.crocsandcoffee.seattleplacesearch.R
import com.crocsandcoffee.seattleplacesearch.SeattlePlaceSearchApplication
import com.crocsandcoffee.seattleplacesearch.databinding.ActivityMainBinding
import com.crocsandcoffee.seattleplacesearch.main.ui.fragment.DetailsFragment
import com.crocsandcoffee.seattleplacesearch.main.ui.fragment.SearchFragment
import com.crocsandcoffee.seattleplacesearch.main.viewmodel.MainActivityViewModel
import com.crocsandcoffee.seattleplacesearch.main.viewmodel.NavigationEvent
import javax.inject.Inject

/**
 * @author Omid
 *
 * UI Controller for displaying the following fragments:
 *
 * [SearchFragment] - allows the user to search for a venue and see search results
 * [DetailsFragment] - displays the details of a venue
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var factory: MainActivityViewModel.Factory
    private val viewModel: MainActivityViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {

        (applicationContext as SeattlePlaceSearchApplication)
            .appComponent
            .inject(this)

        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        // only replace if this is the initial create
        if (savedInstanceState == null) {
            replaceFragment(SearchFragment.newInstance())
        }

        subscribe()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun subscribe() {
        viewModel.event.asLiveData().observe(this) { event ->

            when (event) {
                is NavigationEvent.NavigateToDetails -> {
                    replaceFragment(DetailsFragment.newInstance(event.id), addToBackStack = true, DetailsFragment.TAG)
                }
            }
        }
    }

    private fun replaceFragment(fragment: Fragment, addToBackStack: Boolean = false, tag: String? = null) {
        supportFragmentManager.commit {
            setCustomAnimations(
                R.anim.slide_in_right,
                R.anim.slide_out_left,
                R.anim.slide_in_left,
                R.anim.slide_out_right
            )
            replace(R.id.container, fragment)
            if (addToBackStack) addToBackStack(tag)
            setReorderingAllowed(true)
        }
    }
}