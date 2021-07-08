package com.journey.posts

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.liveData
import com.journey.LiveDataTestUtil
import com.journey.MainCoroutineRule
import com.journey.TestUtils
import com.journey.common.Result
import com.journey.common.Status
import com.journey.posts.data.repository.PostsRepository
import com.journey.posts.domain.PostsUseCase
import com.journey.posts.domain.PostsUseCaseImpl
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class PostsUseCaseTest {
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    lateinit var useCase: PostsUseCase

    lateinit var repository: PostsRepository

    private val postData = TestUtils.postsResponse

    @Test
    fun testGetPostsLoading() = mainCoroutineRule.runBlockingTest {

        repository = mock {
            onBlocking {
                getPosts()
            } doReturn liveData {
                emit(Result.loading())
            }
        }

        useCase = PostsUseCaseImpl(repository)

        val result = useCase.getPosts()

        assert(LiveDataTestUtil.getValue(result).status == Status.LOADING)
    }

    @Test
    fun testGetPostDetailsSuccess() = mainCoroutineRule.runBlockingTest {

        repository = mock {
            onBlocking {
                getPosts()
            } doReturn liveData {
                emit(Result.success(postData))
            }
        }

        useCase = PostsUseCaseImpl(repository)

        val result = useCase.getPosts()
        assert(
            LiveDataTestUtil.getValue(result).data == postData &&
                    LiveDataTestUtil.getValue(result).status == Status.SUCCESS
        )
    }

    @Test
    fun testGetPostDetailsErrorData() = mainCoroutineRule.runBlockingTest {
        repository = mock {
            onBlocking { getPosts() } doReturn liveData {
                emit(Result.error("Posts not found"))
            }
        }
        useCase = PostsUseCaseImpl(repository)
        val result = useCase.getPosts()
        result.observeForever { }
        assert(
            LiveDataTestUtil.getValue(result).status == Status.ERROR && LiveDataTestUtil.getValue(
                result
            ).message == "Posts not found"
        )

    }

}