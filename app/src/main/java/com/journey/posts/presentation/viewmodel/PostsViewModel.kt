package com.journey.posts.presentation.viewmodel

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.journey.common.Event
import com.journey.common.Result
import com.journey.posts.data.remote.models.PostsResponse
import com.journey.posts.domain.PostsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostsViewModel @Inject constructor(
        private val mPostsUseCase: PostsUseCase
) : ViewModel() {

    val postsResult = MediatorLiveData<Event<Result<PostsResponse>>>()

    init {
        fetchPosts()
    }

    fun fetchPosts() {
        viewModelScope.launch {
            postsResult.addSource(mPostsUseCase.getPosts()) {
                postsResult.value = Event(it)
            }
        }
    }
}