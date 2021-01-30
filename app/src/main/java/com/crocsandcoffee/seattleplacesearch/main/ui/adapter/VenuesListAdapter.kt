package com.crocsandcoffee.seattleplacesearch.main.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.RequestManager
import com.crocsandcoffee.seattleplacesearch.main.viewmodel.VenueListItem

/**
 * @author Omid
 *
 * [ListAdapter] used to display a list of [VenueListItem] by [SearchFragment]
 */
class VenuesListAdapter(
    private val glide: RequestManager,
    private val onClick: VenueItemOnClick
) : ListAdapter<VenueListItem, VenueItemViewHolder>(VenueListItem.DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VenueItemViewHolder {
        return VenueItemViewHolder.create(parent, glide, onClick)
    }

    override fun onBindViewHolder(holder: VenueItemViewHolder, position: Int) = holder.bind(getItem(position))

}