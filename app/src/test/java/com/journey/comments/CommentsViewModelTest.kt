package com.journey.comments

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.journey.LiveDataTestUtil
import com.journey.MainCoroutineRule
import com.journey.TestUtils
import com.journey.comments.data.remote.models.CommentResponse
import com.journey.comments.domain.CommentUseCase
import com.journey.comments.presentation.viewmodel.CommentViewModel
import com.journey.common.Result
import com.journey.common.Status
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CommentsViewModelTest {
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var useCase: CommentUseCase

    private lateinit var viewModel: CommentViewModel

    private val commentsData = TestUtils.commentsResponse


    @Before
    fun init() {
        useCase = mock()
    }

    @Test
    fun testGetCommentsForPostLoadingData() = mainCoroutineRule.runBlockingTest {
        useCase = mock {
            onBlocking { getComments(1) } doReturn liveData {
                emit(Result.loading())
            }
        }
        viewModel = CommentViewModel(useCase)
        viewModel.fetchCommentsToPost(1)
        val result = viewModel.commentResult
        result.observeForever { }
        kotlinx.coroutines.delay(2000)
        assert(LiveDataTestUtil.getValue(result).peekContent().status == Status.LOADING)
    }

    @Test
    fun testGetCommentsForPostSuccessData() = mainCoroutineRule.runBlockingTest {

        useCase = mock {
            onBlocking { getComments(1) } doReturn liveData {
                emit(Result.success(commentsData))
            }
        }

        viewModel = CommentViewModel(useCase)
        viewModel.fetchCommentsToPost(1)

        val result = viewModel.commentResult
        result.observeForever {}
        kotlinx.coroutines.delay(2000)
        assert(
                LiveDataTestUtil.getValue(result).peekContent().status == Status.SUCCESS &&
                        LiveDataTestUtil.getValue(result).peekContent().data == commentsData
        )
    }

    @Test
    fun testGetCommentsForPostErrorData() = mainCoroutineRule.runBlockingTest {
        useCase = mock {
            onBlocking { getComments(1) } doReturn object :
                    LiveData<Result<CommentResponse>>() {
                init {
                    value = Result.error("error")
                }
            }
        }

        viewModel = CommentViewModel(useCase)
        viewModel.fetchCommentsToPost(1)
        val result = viewModel.commentResult
        result.observeForever {}
        kotlinx.coroutines.delay(2000)
        assert(
                LiveDataTestUtil.getValue(result).peekContent().status == Status.ERROR &&
                        LiveDataTestUtil.getValue(result).peekContent().message == "error"
        )
    }

}