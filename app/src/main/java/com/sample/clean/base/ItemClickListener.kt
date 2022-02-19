package com.sample.clean.base

import android.view.View

interface ItemClickListener {
    fun onItemClick(position: Int, view: View)
}