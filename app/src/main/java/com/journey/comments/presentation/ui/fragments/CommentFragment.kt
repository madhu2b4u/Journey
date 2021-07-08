package com.journey.comments.presentation.ui.fragments


import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.journey.R
import com.journey.comments.data.remote.models.CommentResponse
import com.journey.comments.data.remote.models.Comments
import com.journey.comments.presentation.ui.adapter.CommentsAdapter
import com.journey.comments.presentation.viewmodel.CommentViewModel
import com.journey.common.*
import com.journey.posts.data.remote.models.Post
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_comments.*
import kotlinx.android.synthetic.main.fragment_comments.etSearch
import kotlinx.android.synthetic.main.fragment_posts.*
import kotlinx.android.synthetic.main.fragment_posts.loader
import kotlinx.android.synthetic.main.list_item_posts.*

@AndroidEntryPoint
class CommentFragment : BaseFragment() {

    var commentAdapter: CommentsAdapter = CommentsAdapter()

    private val mCommentViewModel: CommentViewModel by viewModels()

    private var commentList = ArrayList<Comments>()

    override fun layoutId(): Int {
        return R.layout.fragment_comments
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launchWhenStarted {
            try {
                setPostViews()
                setCommentViews()
                observeCommentsLiveData()
            } finally {
                // This line might execute after Lifecycle is DESTROYED.
                if (lifecycle.currentState >= Lifecycle.State.STARTED) {
                    // Here, since we've checked, it is safe to run any
                    // Fragment transactions.
                }
            }
        }
    }

    private fun observeCommentsLiveData() {
        mCommentViewModel.commentResult.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let { comments ->
                commentsObserver(comments)
            }
            commentsObserver(it.peekContent())
        })
    }

    private fun commentsObserver(comments: Result<CommentResponse>) {
        when (comments.status) {
            Status.LOADING -> {
                showLoader()
            }
            Status.ERROR -> {
                activity?.shortToast(comments.message.toString())
                hideLoader()
            }
            Status.SUCCESS -> {
                hideLoader()
                comments.data?.let { comments ->
                    if (comments.isNotEmpty()) {
                        commentList = comments
                        commentAdapter.updateComments(comments)
                    }

                }
            }
        }
    }

    private fun showLoader() {
        loader.visibility = View.VISIBLE
        rvCommetns.visibility = View.GONE
    }

    private fun hideLoader() {
        loader.visibility = View.GONE
        rvCommetns.visibility = View.VISIBLE
    }

    private fun setPostViews() {
        val post: Post = arguments?.getSerializable("post") as Post
        activity?.title = getString(R.string.comments)
        mCommentViewModel.fetchCommentsToPost(postId = post.id)
        tvTitle.text = post.title
        tvPost.text = post.body

        etSearch.clearFocus()
        etSearch.afterTextChanged {
            filter(it)
        }
    }

    private fun setCommentViews() {
        RecyclerViewExtension.setItemDecoration(rvCommetns)
        rvCommetns.adapter = commentAdapter
    }

    //search through  comment by name or body

    private fun filter(text: String) {
        val tempList: ArrayList<Comments> = ArrayList()
        for (comment in commentList) {
            if (comment.name.toLowerCase().contains(text.toLowerCase()) || comment.email.toLowerCase().contains(text.toLowerCase())) {
                tempList.add(comment)
            }
        }
        commentAdapter.updateComments(tempList)
    }


}
