package com.journey.posts.di

import com.journey.posts.data.repository.PostsRepository
import com.journey.posts.data.repository.PostsRepositoryImpl
import com.journey.posts.domain.PostsUseCaseImpl
import com.journey.posts.domain.PostsUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class PostsDomainModule {

    @Binds
    abstract fun bindsRepository(
        repoImpl: PostsRepositoryImpl
    ): PostsRepository


    @Binds
    abstract fun bindsPostsUseCase(
        mPostsUseCase: PostsUseCaseImpl
    ): PostsUseCase


}