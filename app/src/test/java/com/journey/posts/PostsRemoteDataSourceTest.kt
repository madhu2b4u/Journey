package com.journey.posts


import com.journey.MainCoroutineRule
import com.journey.TestUtils
import com.journey.posts.data.remote.services.PostsService
import com.journey.posts.data.remote.source.PostsRemoteDataSource
import com.journey.posts.data.remote.source.PostsRemoteDataSourceImpl
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class PostsRemoteDataSourceTest {
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    lateinit var service: PostsService

    lateinit var dataSource: PostsRemoteDataSource

    @Test
    fun getPosts() = runBlocking {

        service = mock {
            onBlocking {
                getPostsAsync()
            } doReturn GlobalScope.async {
                Response.success(TestUtils.postsResponse)
            }
        }

        dataSource =
            PostsRemoteDataSourceImpl(service, mainCoroutineRule.coroutineContext)

        val result = dataSource.getPosts()

        assert(result == TestUtils.postsResponse)
    }

    @Test(expected = Exception::class)
    fun getPostsThrowException() = runBlocking {
        service = mock {
            onBlocking {
                getPostsAsync()
            } doReturn GlobalScope.async {
               throw Exception()
            }
        }

        dataSource = PostsRemoteDataSourceImpl(service, mainCoroutineRule.coroutineContext)

        val result = dataSource.getPosts()
        assert(result == TestUtils.postsResponse)
    }


}