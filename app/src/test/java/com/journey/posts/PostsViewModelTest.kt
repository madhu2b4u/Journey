package com.journey.posts

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.journey.LiveDataTestUtil
import com.journey.MainCoroutineRule
import com.journey.TestUtils
import com.journey.common.Result
import com.journey.common.Status
import com.journey.posts.data.remote.models.PostsResponse
import com.journey.posts.domain.PostsUseCase
import com.journey.posts.presentation.viewmodel.PostsViewModel
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class PostsViewModelTest {
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var useCase: PostsUseCase

    private lateinit var viewModel: PostsViewModel

    private val postData = TestUtils.postsResponse


    @Before
    fun init() {
        useCase = mock()
    }

    @Test
    fun testGetPostsLoadingData() = mainCoroutineRule.runBlockingTest {
        useCase = mock {
            onBlocking { getPosts() } doReturn liveData {
                emit(Result.loading())
            }
        }
        viewModel = PostsViewModel(useCase)
        viewModel.fetchPosts()
        val result = viewModel.postsResult
        result.observeForever { }
        kotlinx.coroutines.delay(2000)
        assert(LiveDataTestUtil.getValue(result).peekContent().status == Status.LOADING)
    }

    @Test
    fun testGetPostsSuccessData() = mainCoroutineRule.runBlockingTest {

        useCase = mock {
            onBlocking { getPosts() } doReturn liveData {
                emit(Result.success(postData))
            }
        }

        viewModel = PostsViewModel(useCase)
        viewModel.fetchPosts()

        val result = viewModel.postsResult
        result.observeForever {}
        kotlinx.coroutines.delay(2000)
        assert(
            LiveDataTestUtil.getValue(result).peekContent().status == Status.SUCCESS &&
                    LiveDataTestUtil.getValue(result).peekContent().data == postData
        )
    }

    @Test
    fun testGetPostsErrorData() = mainCoroutineRule.runBlockingTest {
        useCase = mock {
            onBlocking { getPosts() } doReturn object :
                LiveData<Result<PostsResponse>>() {
                init {
                    value = Result.error("error")
                }
            }
        }

        viewModel = PostsViewModel(useCase)
        viewModel.fetchPosts()
        val result = viewModel.postsResult
        result.observeForever {}
        kotlinx.coroutines.delay(2000)
        assert(
            LiveDataTestUtil.getValue(result).peekContent().status == Status.ERROR &&
                    LiveDataTestUtil.getValue(result).peekContent().message == "error"
        )
    }

}