package com.journey.comments


import com.journey.MainCoroutineRule
import com.journey.TestUtils
import com.journey.comments.data.remote.services.CommentService
import com.journey.comments.data.remote.source.CommentRemoteDataSource
import com.journey.comments.data.remote.source.CommentRemoteDataSourceImpl
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
class CommentsRemoteDataSourceTest {
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    lateinit var service: CommentService

    lateinit var dataSource: CommentRemoteDataSource

    @Test
    fun getCommentsForPost() = runBlocking {

        service = mock {
            onBlocking {
                getCommentsAsync(1)
            } doReturn GlobalScope.async {
                Response.success(TestUtils.commentsResponse)
            }
        }

        dataSource =
                CommentRemoteDataSourceImpl(service, mainCoroutineRule.coroutineContext)

        val result = dataSource.getComments(1)

        assert(result == TestUtils.commentsResponse)
    }

    @Test(expected = Exception::class)
    fun getCommentsForPostThrowException() = runBlocking {
        service = mock {
            onBlocking {
                getCommentsAsync(1)
            } doReturn GlobalScope.async {
                throw Exception()
            }
        }

        dataSource = CommentRemoteDataSourceImpl(service, mainCoroutineRule.coroutineContext)

        val result = dataSource.getComments(1)
        assert(result == TestUtils.commentsResponse)
    }


}