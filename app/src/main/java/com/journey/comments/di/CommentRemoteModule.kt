package com.journey.comments.di


import com.journey.comments.data.remote.services.CommentService
import com.journey.comments.data.remote.source.CommentRemoteDataSource
import com.journey.comments.data.remote.source.CommentRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit


@Module(includes = [CommentRemoteModule.Binders::class])
@InstallIn(SingletonComponent::class)
class CommentRemoteModule {
    @Module
    @InstallIn(SingletonComponent::class)
    interface Binders {
        @Binds
        fun bindsRemoteSource(
                remoteDataSourceImpl: CommentRemoteDataSourceImpl
        ): CommentRemoteDataSource
    }

    @Provides
    fun providesCommentService(retrofit: Retrofit): CommentService =
            retrofit.create(CommentService::class.java)
}