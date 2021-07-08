package com.journey.posts.data.repository

import androidx.lifecycle.LiveData
import com.journey.posts.data.remote.models.PostsResponse
import com.journey.common.Result


interface PostsRepository {
    suspend fun getPosts(): LiveData<Result<PostsResponse>>

}