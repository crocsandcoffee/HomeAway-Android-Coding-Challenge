package com.crocsandcoffee.seattleplacesearch.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.crocsandcoffee.seattleplacesearch.dagger.MainDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class SearchFragmentViewModel(
    private val mainDispatcher: CoroutineDispatcher = Dispatchers.Main
) : ViewModel() {


    class Factory @Inject constructor(
        @MainDispatcher private val mainDispatcher: CoroutineDispatcher
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {

            @Suppress("UNCHECKED_CAST")
            return SearchFragmentViewModel(mainDispatcher) as T
        }

    }
}