package com.journey.posts.presentation.ui.fragments


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.journey.R
import com.journey.common.BaseFragment
import com.journey.common.Event
import com.journey.common.Status
import com.journey.posts.data.remote.models.PostsResponse
import com.journey.posts.presentation.viewmodel.PostsViewModel
import dagger.hilt.android.AndroidEntryPoint
import com.journey.common.Result


@AndroidEntryPoint
class PostsFragment : BaseFragment() {

    private val mPostsViewModel: PostsViewModel by viewModels()

    override fun layoutId(): Int {
        return R.layout.fragment_posts
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launchWhenStarted {
            try {
                mPostsViewModel.fetchPosts()
                observePostsLiveData()

            } finally {

            }
        }
    }

    private fun observePostsLiveData() {
        mPostsViewModel.postsResult.observe(viewLifecycleOwner, Observer {

            it.getContentIfNotHandled()?.let {
                    posts -> postsObserver(posts)
            }

            postsObserver(it.peekContent())
        })
    }

    private fun postsObserver(it: Result<PostsResponse>) {
        when (it.status) {
            Status.LOADING -> {

            }
            Status.ERROR -> {

            }
            Status.SUCCESS -> {
                it.data?.let { posts ->

                }
            }
        }
    }


}
