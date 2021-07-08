package com.journey.common

import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewExtension {

    companion object {

        fun setItemDecoration(recyclerView: RecyclerView) {
            val layoutManager = LinearLayoutManager(
                    recyclerView.context,
                    RecyclerView.VERTICAL,
                    false
            ).apply {
                recyclerView.layoutManager = this
            }

            DividerItemDecoration(
                    recyclerView.context,
                    layoutManager.orientation
            ).apply {
                recyclerView.addItemDecoration(this)
            }
        }

    }


}