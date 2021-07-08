package com.journey.posts.presentation.viewmodel

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.journey.common.Event
import com.journey.posts.data.remote.models.PostsResponse
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.journey.posts.domain.PostsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import com.journey.common.Result

@HiltViewModel
class PostsViewModel @Inject constructor(
    private val mPostsUseCase: PostsUseCase
) : ViewModel() {

    val postsResult = MediatorLiveData<Event<Result<PostsResponse>>>()

    fun fetchPosts() {
        viewModelScope.launch {
            postsResult.addSource(mPostsUseCase.getPosts()) {
                postsResult.value = Event(it)
            }
        }
    }
}