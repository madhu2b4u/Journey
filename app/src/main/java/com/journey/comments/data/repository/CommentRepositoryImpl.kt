package com.journey.comments.data.repository


import androidx.lifecycle.liveData
import com.journey.comments.data.remote.source.CommentRemoteDataSource
import com.journey.common.Result
import javax.inject.Inject


class CommentRepositoryImpl @Inject constructor(
        private val remoteDataSource: CommentRemoteDataSource
) : CommentRepository {
    override suspend fun getComments(postId: Int) = liveData {
        emit(Result.loading())
        try {
            val response = remoteDataSource.getComments(postId)
            emit(Result.success(response))

        } catch (exception: Exception) {
            emit(Result.error(exception.message ?: ""))
        }
    }


}