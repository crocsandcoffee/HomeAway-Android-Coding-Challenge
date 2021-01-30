package com.crocsandcoffee.seattleplacesearch.main.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.net.toUri
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import com.bumptech.glide.Glide
import com.crocsandcoffee.seattleplacesearch.R
import com.crocsandcoffee.seattleplacesearch.SeattlePlaceSearchApplication
import com.crocsandcoffee.seattleplacesearch.common.SavedStateVMFactory
import com.crocsandcoffee.seattleplacesearch.databinding.FragmentDetailsBinding
import com.crocsandcoffee.seattleplacesearch.main.viewmodel.DetailsFragmentViewModel
import com.crocsandcoffee.seattleplacesearch.main.viewmodel.DetailsUiState
import javax.inject.Inject

/**
 * @author Omid
 *
 * TODO:
 */
class DetailsFragment : Fragment(R.layout.fragment_details) {


    companion object {
        const val TAG = "DetailsFragment"
        const val VENUE_ID_KEY = "$TAG.VENUE_ID"
        fun newInstance(venueId: String) = DetailsFragment().apply {
            arguments = bundleOf(VENUE_ID_KEY to venueId)
        }
    }

    @Inject
    lateinit var factory: DetailsFragmentViewModel.Factory
    private val viewModel: DetailsFragmentViewModel by viewModels {
        SavedStateVMFactory(factory, this, arguments)
    }

    // Scoped to the lifecycle of the fragment's view (between onCreateView and onDestroyView)
    private var _binding: FragmentDetailsBinding? = null
    private val binding: FragmentDetailsBinding
        get() = _binding!!

    override fun onAttach(context: Context) {

        super.onAttach(context)

        (requireContext().applicationContext as SeattlePlaceSearchApplication)
            .appComponent
            .inject(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                activity?.onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDetailsBinding.bind(view)


        (requireActivity() as AppCompatActivity).apply {
            setSupportActionBar(binding.toolbar)
            supportActionBar?.apply {
                setDisplayHomeAsUpEnabled(true)
                setDisplayShowTitleEnabled(false)
            }
        }

        viewModel.loadDetails()

        subscribe()
        setOnClickListeners()
    }

    override fun onDestroyView() {
        // Consider not storing the binding instance in a field, if not needed.
        _binding = null
        super.onDestroyView()
    }

    private fun setOnClickListeners() {
        binding.venueDetails.tryAgain.setOnClickListener {
            viewModel.loadDetails()
            binding.venueDetails.tryAgain.isEnabled = false
        }
    }

    private fun subscribe() {

        viewModel.state.asLiveData().observe(viewLifecycleOwner) { state ->
            render(state)
        }
    }

    private fun render(state: DetailsUiState) {
        when (state) {
            DetailsUiState.Loading -> {
                binding.venueDetails.loadingProgressBar.isVisible = true
                binding.venueDetails.tryAgain.isVisible = false
                binding.venueDetails.errorText.isVisible = false
                binding.venueDetails.statsCard.isVisible = false
                binding.venueDetails.infoCard.isVisible = false
            }
            DetailsUiState.Error -> {
                binding.venueDetails.loadingProgressBar.isVisible = false
                binding.venueDetails.errorText.isVisible = true
                binding.venueDetails.tryAgain.isVisible = true
                binding.venueDetails.tryAgain.isEnabled = true
                binding.venueDetails.statsCard.isVisible = false
                binding.venueDetails.infoCard.isVisible = false

            }
            is DetailsUiState.Success -> {
                binding.venueDetails.loadingProgressBar.isVisible = false
                binding.venueDetails.tryAgain.isVisible = false
                binding.venueDetails.errorText.isVisible = false
                binding.venueDetails.statsCard.isVisible = true
                binding.venueDetails.infoCard.isVisible = true

                Glide.with(this).load(state.bestPhoto).into(binding.venueDetails.bestPhoto)
                Glide.with(this).load(state.mapUrl).into(binding.map)
                Glide.with(this).load(state.categoryIconUri).into(binding.venueDetails.venueIcon)

                binding.venueDetails.venueCategory.isVisible = state.categoryName.isNotEmpty()
                binding.venueDetails.venueCategory.text = state.categoryName

                binding.venueDetails.verified.isVisible = state.isVenueVerified

                binding.venueDetails.venueContact.isVisible = !state.contact.isNullOrEmpty()
                binding.venueDetails.venueContact.text = String.format(getString(R.string.contact), state.contact)

                val sb = StringBuilder()

                if (!state.address.isNullOrEmpty()) {
                    sb.append(state.address).append("\n")
                }

                if (!state.city.isNullOrEmpty()) {
                    sb.append(state.city).append(", ")
                }

                if (!state.state.isNullOrEmpty()) {
                    sb.append(state.state).append(" ")
                }

                if (!state.postalCode.isNullOrEmpty()) {
                    sb.append(state.postalCode)
                }

                val address = sb.toString()
                binding.venueDetails.venueFullAddress.isVisible = address.isNotEmpty()
                binding.venueDetails.venueFullAddress.text = address

                binding.venueDetails.likes.isVisible = state.likes.isNotEmpty()
                binding.venueDetails.likes.text = state.likes

                binding.venueDetails.venueWebsite.isVisible = state.venueUrl.isNotEmpty()
                binding.venueDetails.venueWebsite.text = state.venueUrl
                binding.venueDetails.venueWebsite.setOnClickListener {
                    CustomTabsIntent
                        .Builder()
                        .build()
                        .launchUrl(requireContext(), state.venueUrl.toUri())
                }
            }
        }
    }

}