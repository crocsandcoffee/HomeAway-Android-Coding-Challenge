package com.crocsandcoffee.seattleplacesearch.main.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.crocsandcoffee.seattleplacesearch.R
import com.crocsandcoffee.seattleplacesearch.SeattlePlaceSearchApplication
import com.crocsandcoffee.seattleplacesearch.dagger.ClientID
import com.crocsandcoffee.seattleplacesearch.dagger.ClientSecret
import com.crocsandcoffee.seattleplacesearch.dagger.MapsApiKey
import com.crocsandcoffee.seattleplacesearch.databinding.DetailsFragmentBinding
import javax.inject.Inject

class DetailsFragment : Fragment(R.layout.details_fragment) {


    companion object {
        const val TAG = "DetailsFragment"
        fun newInstance() = DetailsFragment()
    }

    @Inject
    @MapsApiKey
    lateinit var mapsApiKey: String

    @Inject
    @ClientSecret
    lateinit var clientSecret: String

    @Inject
    @ClientID
    lateinit var clientID: String

    // Scoped to the lifecycle of the fragment's view (between onCreateView and onDestroyView)
    private var _binding: DetailsFragmentBinding? = null
    private val binding: DetailsFragmentBinding
        get() = _binding!!

    override fun onAttach(context: Context) {

        super.onAttach(context)

        (requireContext().applicationContext as SeattlePlaceSearchApplication)
            .appComponent
            .inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = DetailsFragmentBinding.bind(view)

        Glide
            .with(this)
            .load("https://maps.googleapis.com/maps/api/staticmap?center=Berkeley,CA&zoom=14&size=400x400&key=$mapsApiKey".toUri())
            .into(binding.map)
    }

    override fun onDestroyView() {
        // Consider not storing the binding instance in a field, if not needed.
        _binding = null
        super.onDestroyView()
    }

}