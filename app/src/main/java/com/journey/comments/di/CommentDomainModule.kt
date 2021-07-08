package com.journey.comments.di

import com.journey.comments.data.repository.CommentRepository
import com.journey.comments.data.repository.CommentRepositoryImpl
import com.journey.comments.domain.CommentUseCase
import com.journey.comments.domain.CommentUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class CommentDomainModule {
    @Binds
    abstract fun bindsRepository(
            repoImpl: CommentRepositoryImpl
    ): CommentRepository

    @Binds
    abstract fun bindsCommentsUseCase(
            mCommentUseCase: CommentUseCaseImpl
    ): CommentUseCase


}