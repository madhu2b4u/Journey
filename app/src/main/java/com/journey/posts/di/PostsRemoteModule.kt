package com.journey.posts.di


import com.journey.posts.data.remote.services.PostsService
import com.journey.posts.data.remote.source.PostsRemoteDataSource
import com.journey.posts.data.remote.source.PostsRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit


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