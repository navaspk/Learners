package com.sample.clean.base

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerAdapter<T>(
    private val itemClickListener: ItemClickListener? = null
) : RecyclerView.Adapter<BaseViewHolder<T>>() {

    private var items = ArrayList<T>()

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<T> {

        val viewHolder = createBaseViewHolder(
            parent = parent,
            viewType = viewType
        )

        viewHolder.setItemClickListener(itemClickListener)
        return viewHolder
    }

    override fun onBindViewHolder(
        holder: BaseViewHolder<T>,
        position: Int
    ) = holder.bindView(item = items[position])


    fun setItems(items: ArrayList<T>) {
        this.items = items
        notifyDataSetChanged()
    }

    fun getItems(): ArrayList<T> {
        val list = arrayListOf<T>()
        list.addAll(items)
        return list
    }

    fun clearItems() {
        items.clear()
        notifyDataSetChanged()
    }

    /**
     * To show updated content with animated insertion
     */
    fun diffUtilRefresh(
        diffResult: DiffUtil.DiffResult,
        newList: ArrayList<T>
    ) {
        items.clear()
        items.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }

    protected abstract fun createBaseViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<T>
}