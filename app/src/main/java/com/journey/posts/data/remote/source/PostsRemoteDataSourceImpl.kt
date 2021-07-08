package com.journey.posts.data.remote.source

import com.journey.di.qualifiers.IO
import com.journey.posts.data.remote.models.PostsResponse
import com.journey.posts.data.remote.services.PostsService
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext


class PostsRemoteDataSourceImpl @Inject constructor(
    private val service: PostsService,
    @IO private val context: CoroutineContext
) : PostsRemoteDataSource {
    override suspend fun getPosts(): PostsResponse =
        withContext(context) {
            val response = service.getPostsAsync().await()
            if (response.isSuccessful)
                response.body() ?: throw Exception("no response")
            else
                throw Exception("invalid request with code ${response.code()}")
        }

}
