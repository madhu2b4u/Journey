package com.journey.posts.data.remote.source

import com.journey.posts.data.remote.models.PostsResponse


interface PostsRemoteDataSource {
    suspend fun getPosts(): PostsResponse
}