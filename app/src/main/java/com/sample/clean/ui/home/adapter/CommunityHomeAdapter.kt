package com.sample.clean.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.sample.clean.base.BaseRecyclerAdapter
import com.sample.clean.base.BaseViewHolder
import com.sample.clean.base.ItemClickListener
import com.sample.clean.databinding.ItemCommunityHomeBinding
import com.sample.clean.servermodel.ResponseItem
import com.sample.clean.ui.event.ClickEvent

/**
 * Adapter class which create the view for the item
 */
class CommunityHomeAdapter(
    itemClickListener: ItemClickListener, private var event: (ClickEvent) -> Unit
) : BaseRecyclerAdapter<ResponseItem>(itemClickListener) {

    override fun createBaseViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<ResponseItem> {
        return CommunityHomeViewHolder(
            ItemCommunityHomeBinding.inflate(LayoutInflater.from(parent.context)), event
        )
    }

}