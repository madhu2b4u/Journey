package com.journey.posts.di


import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import com.journey.posts.data.remote.source.PostsRemoteDataSource
import com.journey.posts.data.remote.source.PostsRemoteDataSourceImpl
import com.journey.posts.data.remote.services.PostsService
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module(includes = [PostsRemoteModule.Binders::class])
@InstallIn(SingletonComponent::class)
class PostsRemoteModule {
    @Module
    @InstallIn(SingletonComponent::class)
    interface Binders {
        @Binds
        fun bindsRemoteSource(
            remoteDataSourceImpl: PostsRemoteDataSourceImpl
        ): PostsRemoteDataSource
    }
    @Provides
    fun providesPostsService(retrofit: Retrofit): PostsService =
        retrofit.create(PostsService::class.java)

}