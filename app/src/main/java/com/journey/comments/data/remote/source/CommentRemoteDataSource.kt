package com.journey.comments.data.remote.source

import com.journey.comments.data.remote.models.CommentResponse


interface CommentRemoteDataSource {
    suspend fun getComments(postId: Int): CommentResponse
}