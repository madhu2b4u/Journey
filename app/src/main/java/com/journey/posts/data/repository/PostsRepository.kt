package com.journey.posts.data.repository

import androidx.lifecycle.LiveData
import com.journey.common.Result
import com.journey.posts.data.remote.models.PostsResponse


interface PostsRepository {
    suspend fun getPosts(): LiveData<Result<PostsResponse>>

}