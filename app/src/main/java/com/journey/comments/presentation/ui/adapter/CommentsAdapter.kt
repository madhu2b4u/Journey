package com.journey.comments.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.journey.R
import com.journey.comments.data.remote.models.Comments
import kotlinx.android.synthetic.main.list_item_comments.view.*
import javax.inject.Singleton

@Singleton
class CommentsAdapter : RecyclerView.Adapter<CommentsAdapter.CommentsViewHolder>() {

    private var comments = ArrayList<Comments>()

    fun updateComments(comments: ArrayList<Comments>) {
        this.comments = comments
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentsViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_comments, parent, false)
        return CommentsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return comments.size
    }

    override fun onBindViewHolder(holderSuggested: CommentsViewHolder, position: Int) {
        return holderSuggested.bind(comments[position], position)
    }

    inner class CommentsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(comments: Comments, pos: Int) {
            with(itemView) {
                tvName.text = comments.name
                tvEmail.text = comments.email
                tvBody.text = comments.body
            }

        }
    }
}




