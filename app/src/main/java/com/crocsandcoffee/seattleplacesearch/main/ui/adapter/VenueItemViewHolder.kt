package com.crocsandcoffee.seattleplacesearch.main.ui.adapter

import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.crocsandcoffee.seattleplacesearch.R
import com.crocsandcoffee.seattleplacesearch.common.inflate
import com.crocsandcoffee.seattleplacesearch.databinding.VenueListItemBinding
import com.crocsandcoffee.seattleplacesearch.main.viewmodel.VenueListItem

class VenueItemViewHolder(
    private val binding: VenueListItemBinding,
    private val glide: RequestManager,
    private val onClick: VenueItemOnClick
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: VenueListItem) {

        binding.venueName.text = item.name
        binding.venueCategory.text = item.category

        binding.venueDistance.text = String.format(binding.root.context.getString(R.string.distance_label), item.distance)
        binding.venueDistance.isVisible = !item.distance.isNullOrEmpty()

        glide.load(item.iconUri).into(binding.venueIcon)

        binding.root.setOnClickListener {
            onClick(item)
        }

    }

    companion object {

        /**
         * Create and return an instance of [VenueItemViewHolder]
         */
        fun create(parent: ViewGroup, glide: RequestManager, onClick: VenueItemOnClick): VenueItemViewHolder {
            return VenueItemViewHolder(
                VenueListItemBinding.bind(parent.inflate(R.layout.venue_list_item)),
                glide,
                onClick
            )
        }
    }
}

typealias VenueItemOnClick = (VenueListItem) -> Unit