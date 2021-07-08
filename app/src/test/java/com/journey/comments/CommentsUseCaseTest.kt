package com.journey.comments

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.liveData
import com.journey.LiveDataTestUtil
import com.journey.MainCoroutineRule
import com.journey.TestUtils
import com.journey.comments.data.repository.CommentRepository
import com.journey.comments.domain.CommentUseCase
import com.journey.comments.domain.CommentUseCaseImpl
import com.journey.common.Result
import com.journey.common.Status
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CommentsUseCaseTest {
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    lateinit var useCase: CommentUseCase

    lateinit var repository: CommentRepository

    private val commentsData = TestUtils.commentsResponse

    @Test
    fun testGetCommentsForPostLoading() = mainCoroutineRule.runBlockingTest {

        repository = mock {
            onBlocking {
                getComments(1)
            } doReturn liveData {
                emit(Result.loading())
            }
        }

        useCase = CommentUseCaseImpl(repository)

        val result = useCase.getComments(1)

        assert(LiveDataTestUtil.getValue(result).status == Status.LOADING)
    }

    @Test
    fun testGGetCommentsForPostSuccess() = mainCoroutineRule.runBlockingTest {

        repository = mock {
            onBlocking {
                getComments(1)
            } doReturn liveData {
                emit(Result.success(commentsData))
            }
        }

        useCase = CommentUseCaseImpl(repository)

        val result = useCase.getComments(1)
        assert(
                LiveDataTestUtil.getValue(result).data == commentsData &&
                        LiveDataTestUtil.getValue(result).status == Status.SUCCESS
        )
    }

    @Test
    fun testGetCommentsForPostErrorData() = mainCoroutineRule.runBlockingTest {
        repository = mock {
            onBlocking { getComments(1) } doReturn liveData {
                emit(Result.error("Comments not found"))
            }
        }
        useCase = CommentUseCaseImpl(repository)
        val result = useCase.getComments(1)
        result.observeForever { }
        assert(
                LiveDataTestUtil.getValue(result).status == Status.ERROR && LiveDataTestUtil.getValue(
                        result
                ).message == "Comments not found"
        )

    }

}