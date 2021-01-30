package com.crocsandcoffee.seattleplacesearch.common

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner

/**
 * @author Omid
 *
 * An implementation of [AbstractSavedStateViewModelFactory] that allows us support passing
 * a [SavedStateHandle] to our custom injectable ViewModel factories.
 *
 * In order to obtain an instance of a [ViewModel] that takes additional constructor params,
 * an implementation of [SavedStateFactory] should be passed which holds all the [ViewModel]
 * constructor params, and [SavedStateFactory.create] will be called with the [SavedStateHandle]
 * that also needs to be passed to the [ViewModel] constructor.
 */
class SavedStateVMFactory<out V : ViewModel>(
    private val vmFactory: SavedStateFactory<V>,
    owner: SavedStateRegistryOwner,
    defaultArgs: Bundle? = null
) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(key: String, modelClass: Class<T>, handle: SavedStateHandle): T {
        return vmFactory.create(handle) as T
    }
}

interface SavedStateFactory<T : ViewModel> {
    fun create(handle: SavedStateHandle): T
}