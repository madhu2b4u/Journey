package com.journey.comments.domain

import androidx.lifecycle.LiveData
import com.journey.comments.data.remote.models.CommentResponse
import com.journey.common.Result

interface CommentUseCase {
    suspend fun getComments(postId: Int): LiveData<Result<CommentResponse>>
}