package com.journey.comments.data.remote.source

import com.journey.comments.data.remote.models.CommentResponse
import com.journey.comments.data.remote.services.CommentService
import com.journey.di.qualifiers.IO
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext


class CommentRemoteDataSourceImpl @Inject constructor(
        private val service: CommentService,
        @IO private val context: CoroutineContext
) : CommentRemoteDataSource {
    override suspend fun getComments(postId: Int): CommentResponse =
            withContext(context) {
                val response = service.getCommentsAsync(postId).await()
                if (response.isSuccessful)
                    response.body() ?: throw Exception("no response")
                else
                    throw Exception("invalid request with code ${response.code()}")
            }

}
