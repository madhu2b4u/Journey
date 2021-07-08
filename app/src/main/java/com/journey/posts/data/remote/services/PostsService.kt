package com.journey.posts.data.remote.services

import com.journey.posts.data.remote.models.PostsResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface PostsService {

    @GET("posts")
    fun getPostsAsync(): Deferred<Response<PostsResponse>>

}