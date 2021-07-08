package com.journey.posts.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.journey.R
import com.journey.posts.data.remote.models.Post
import kotlinx.android.synthetic.main.list_item_posts.view.*
import javax.inject.Singleton

@Singleton
class PostsAdapter : RecyclerView.Adapter<PostsAdapter.PostsViewHolder>() {

    private var posts = ArrayList<Post>()

    private var clickFunction: ((post: Post, pos: Int) -> Unit)? = null

    fun handleClickAction(clickFunction: (Post, Int) -> Unit) {
        this.clickFunction = clickFunction
    }

    fun updatePosts(categories: ArrayList<Post>) {
        this.posts = categories
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_posts, parent, false)
        return PostsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    override fun onBindViewHolder(holderSuggested: PostsViewHolder, position: Int) {
        return holderSuggested.bind(posts[position], position)
    }

    inner class PostsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(post: Post, pos: Int) {
            with(itemView) {
                tvTitle.text = post.title
                tvPost.text = post.body
                setOnClickListener {
                    clickFunction?.invoke(post, pos)
                }
            }

        }
    }
}




