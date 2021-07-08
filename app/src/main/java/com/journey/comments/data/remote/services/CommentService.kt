package com.journey.comments.data.remote.services

import com.journey.comments.data.remote.models.CommentResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CommentService {

    @GET("posts/{id}/comments")
    fun getCommentsAsync(@Path("id") id: Int): Deferred<Response<CommentResponse>>
}