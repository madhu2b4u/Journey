package com.journey.posts.presentation.ui.fragments


import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.journey.R
import com.journey.common.BaseFragment
import com.journey.common.Result
import com.journey.common.Status
import com.journey.posts.data.remote.models.PostsResponse
import com.journey.posts.presentation.ui.adapter.PostsAdapter
import com.journey.posts.presentation.viewmodel.PostsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_posts.*


@AndroidEntryPoint
class PostsFragment : BaseFragment() {

    var postsAdapter: PostsAdapter = PostsAdapter()

    private val mPostsViewModel: PostsViewModel by viewModels()

    override fun layoutId(): Int {
        return R.layout.fragment_posts
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launchWhenStarted {
            try {
                setViews()
                mPostsViewModel.fetchPosts()
                observePostsLiveData()

            } finally {
                // This line might execute after Lifecycle is DESTROYED.
                if (lifecycle.currentState >= Lifecycle.State.STARTED) {
                    // Here, since we've checked, it is safe to run any
                    // Fragment transactions.
                }
            }
        }
    }

    private fun setViews() {
        val layoutManager = LinearLayoutManager(
            requireContext(),
            RecyclerView.VERTICAL,
            false
        ).apply {
            rvPosts.layoutManager = this
        }

        DividerItemDecoration(
            requireContext(),
            layoutManager.orientation
        ).apply {
            rvPosts.addItemDecoration(this)
        }

        rvPosts.adapter = postsAdapter
        postsAdapter.handleClickAction { postItem, i ->

        }
    }

    private fun observePostsLiveData() {
        mPostsViewModel.postsResult.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let { posts ->
                postsObserver(posts)
            }
            postsObserver(it.peekContent())
        })
    }

    private fun postsObserver(it: Result<PostsResponse>) {
        when (it.status) {
            Status.LOADING -> {
                showLoader()
            }
            Status.ERROR -> {
                hideLoader()
            }
            Status.SUCCESS -> {
                hideLoader()
                it.data?.let { posts ->
                    if (posts.isNotEmpty())
                        postsAdapter.updatePosts(posts)

                }
            }
        }
    }

    private fun showLoader() {
        loader.visibility = View.GONE
        rvPosts.visibility = View.VISIBLE
    }

    private fun hideLoader() {
        loader.visibility = View.GONE
        rvPosts.visibility = View.VISIBLE
    }
}
