package com.journey.posts.domain

import com.journey.posts.data.repository.PostsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostsUseCaseImpl @Inject constructor(private val mPostsRepository: PostsRepository) :
    PostsUseCase {
    override suspend fun getPosts()= mPostsRepository.getPosts()

}
