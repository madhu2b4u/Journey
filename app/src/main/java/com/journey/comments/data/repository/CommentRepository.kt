package com.journey.comments.data.repository

import androidx.lifecycle.LiveData
import com.journey.comments.data.remote.models.CommentResponse
import com.journey.common.Result


interface CommentRepository {
    suspend fun getComments(postId: Int): LiveData<Result<CommentResponse>>
}