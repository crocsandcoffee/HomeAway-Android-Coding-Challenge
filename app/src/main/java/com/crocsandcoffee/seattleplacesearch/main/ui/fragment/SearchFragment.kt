package com.crocsandcoffee.seattleplacesearch.main.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.DividerItemDecoration
import com.bumptech.glide.Glide
import com.crocsandcoffee.seattleplacesearch.R
import com.crocsandcoffee.seattleplacesearch.SeattlePlaceSearchApplication
import com.crocsandcoffee.seattleplacesearch.databinding.SearchFragmentBinding
import com.crocsandcoffee.seattleplacesearch.main.ui.adapter.VenuesListAdapter
import com.crocsandcoffee.seattleplacesearch.main.viewmodel.SearchFragmentViewModel
import com.crocsandcoffee.seattleplacesearch.main.viewmodel.SearchState
import javax.inject.Inject

class SearchFragment : Fragment(R.layout.search_fragment) {

    companion object {
        fun newInstance() = SearchFragment()
        private const val LAST_SEARCH_QUERY: String = "last_search_query"
        private const val DEFAULT_QUERY = "Android"
    }

    // Scoped to the lifecycle of the fragment's view (between onCreateView and onDestroyView)
    private var _binding: SearchFragmentBinding? = null
    private val binding: SearchFragmentBinding
        get() = _binding!!

    @Inject
    lateinit var factory: SearchFragmentViewModel.Factory
    private val viewModel: SearchFragmentViewModel by viewModels { factory }

    private val adapter: VenuesListAdapter by lazy {
        VenuesListAdapter(Glide.with(this)) { item ->
            Toast.makeText(requireContext(), "TODO: Show details for ${item.name}", Toast.LENGTH_LONG).show()
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
        _binding = SearchFragmentBinding.bind(view)

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
        binding.searchBar.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                updateListFromInput()
                true
            } else {
                false
            }
        }

        binding.searchBar.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                updateListFromInput()
                true
            } else {
                false
            }
        }
    }

    private fun updateListFromInput() {
        binding.searchBar.text.trim().let { term ->
            if (term.isNotEmpty()) search(term.toString())
        }
    }

    private fun search(term: String) {
        viewModel.query(term)
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

    private fun render(state: SearchState) {
        when (state) {
            SearchState.Loading -> {
                binding.errorText.isVisible = false
                binding.progressBar.isVisible = true
                binding.recyclerView.isVisible = false
                binding.emptyGroup.isVisible = false
            }
            SearchState.Error -> {
                binding.errorText.isVisible = true
                binding.progressBar.isVisible = false
                binding.recyclerView.isVisible = false
                binding.emptyGroup.isVisible = false
            }
            is SearchState.Success -> {

                adapter.submitList(state.items)

                binding.emptyGroup.isVisible = state.items.isEmpty()
                binding.errorText.isVisible = false
                binding.progressBar.isVisible = false
                binding.recyclerView.isVisible = true
            }
        }
    }

}