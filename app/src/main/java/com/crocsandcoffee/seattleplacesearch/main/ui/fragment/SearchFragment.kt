package com.crocsandcoffee.seattleplacesearch.main.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.crocsandcoffee.seattleplacesearch.R
import com.crocsandcoffee.seattleplacesearch.SeattlePlaceSearchApplication
import com.crocsandcoffee.seattleplacesearch.databinding.SearchFragmentBinding
import com.crocsandcoffee.seattleplacesearch.main.viewmodel.SearchFragmentViewModel
import javax.inject.Inject

class SearchFragment : Fragment(R.layout.search_fragment) {

    companion object {
        fun newInstance() = SearchFragment()
        private const val LAST_SEARCH_QUERY: String = "last_search_query"
        private const val DEFAULT_QUERY = "Android"
    }

    // Scoped to the lifecycle of the fragment's view (between onCreateView and onDestroyView)
    private var _binding: SearchFragmentBinding? = null
    private val binding: SearchFragmentBinding = _binding!!

    @Inject
    lateinit var factory: SearchFragmentViewModel.Factory
    private val viewModel: SearchFragmentViewModel by viewModels { factory }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (context.applicationContext as SeattlePlaceSearchApplication)
            .appComponent
            .inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = SearchFragmentBinding.bind(view)


    }

    override fun onDestroyView() {
        // Consider not storing the binding instance in a field, if not needed.
        _binding = null
        super.onDestroyView()
    }
}