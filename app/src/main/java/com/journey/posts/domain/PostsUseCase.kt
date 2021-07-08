package com.journey.posts.domain

import androidx.lifecycle.LiveData
import com.journey.common.Result
import com.journey.posts.data.remote.models.PostsResponse

interface PostsUseCase {
    suspend fun getPosts(): LiveData<Result<PostsResponse>>
}