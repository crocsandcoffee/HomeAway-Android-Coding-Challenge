package com.crocsandcoffee.seattleplacesearch.main.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.DividerItemDecoration
import com.bumptech.glide.Glide
import com.crocsandcoffee.seattleplacesearch.R
import com.crocsandcoffee.seattleplacesearch.SeattlePlaceSearchApplication
import com.crocsandcoffee.seattleplacesearch.databinding.FragmentSearchBinding
import com.crocsandcoffee.seattleplacesearch.main.ui.adapter.VenuesListAdapter
import com.crocsandcoffee.seattleplacesearch.main.viewmodel.MainActivityViewModel
import com.crocsandcoffee.seattleplacesearch.main.viewmodel.SearchFragmentViewModel
import com.crocsandcoffee.seattleplacesearch.main.viewmodel.SearchUiState
import javax.inject.Inject

/**
 * @author Omid
 *
 * [Fragment] UI controller for displaying a search bar to query venues and display a list of the results
 */
class SearchFragment : Fragment(R.layout.fragment_search) {

    companion object {
        fun newInstance() = SearchFragment()
    }

    // Scoped to the lifecycle of the fragment's view (between onCreateView and onDestroyView)
    private var _binding: FragmentSearchBinding? = null
    private val binding: FragmentSearchBinding
        get() = _binding!!

    @Inject
    lateinit var factory: SearchFragmentViewModel.Factory
    private val viewModel: SearchFragmentViewModel by viewModels { factory }

    @Inject
    lateinit var mainVmFactory: MainActivityViewModel.Factory
    private val mainViewModel: MainActivityViewModel by activityViewModels { mainVmFactory }

    /** Adapter to displaying list items */
    private val adapter: VenuesListAdapter by lazy {
        VenuesListAdapter(Glide.with(this)) { item ->
            mainViewModel.launchDetails(item.id)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (requireContext().applicationContext as SeattlePlaceSearchApplication)
            .appComponent
            .inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // bind our already created view to our ViewBinding class
        _binding = FragmentSearchBinding.bind(view)

        (requireActivity() as AppCompatActivity).apply {
            setSupportActionBar(binding.toolbar)
            supportActionBar?.apply {
                setDisplayHomeAsUpEnabled(true)
            }
        }

        setupRecyclerView()
        setupSearchBar()
        subscribe()
    }

    override fun onDestroyView() {
        // Consider not storing the binding instance in a field, if not needed.
        _binding = null
        super.onDestroyView()
    }

    private fun setupSearchBar() {

        binding.inputLayout.editText?.doOnTextChanged { text, _, _, _ ->
            viewModel.query(text?.toString() ?: "")
        }
    }

    private fun setupRecyclerView() {

        // add dividers between RecyclerView's row items
        binding.recyclerView.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
        binding.recyclerView.adapter = adapter
    }

    private fun subscribe() {
        viewModel.state.asLiveData().observe(viewLifecycleOwner) { state ->
            render(state)
        }
    }

    private fun render(state: SearchUiState) {
        when (state) {
            SearchUiState.Error -> {
                binding.errorText.isVisible = true
                binding.recyclerView.isVisible = false
                binding.emptyGroup.isVisible = false
            }
            is SearchUiState.Success -> {

                adapter.submitList(state.items)

                binding.emptyGroup.isVisible = state.items.isEmpty()
                binding.errorText.isVisible = false
                binding.recyclerView.isVisible = true
            }
        }
    }

}