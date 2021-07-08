package com.journey.posts.data.repository


import androidx.lifecycle.liveData
import com.journey.common.Result
import com.journey.posts.data.remote.source.PostsRemoteDataSource
import javax.inject.Inject


class PostsRepositoryImpl @Inject constructor(
    private val remoteDataSource: PostsRemoteDataSource
) : PostsRepository {

    override suspend fun getPosts() = liveData {
        emit(Result.loading())
        try {
            val response = remoteDataSource.getPosts()
            emit(Result.success(response))

        } catch (exception: Exception) {
            emit(Result.error(exception.message ?: ""))
        }
    }
}