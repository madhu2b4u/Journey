package com.journey.comments

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.journey.LiveDataTestUtil
import com.journey.MainCoroutineRule
import com.journey.TestUtils
import com.journey.comments.data.remote.source.CommentRemoteDataSource
import com.journey.comments.data.repository.CommentRepository
import com.journey.comments.data.repository.CommentRepositoryImpl
import com.journey.common.Status
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations


@ExperimentalCoroutinesApi
class CommentsRepositoryTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var repositoryTest: CommentRepository

    @Mock
    lateinit var dataStore: CommentRemoteDataSource

    private val commentsData = TestUtils.commentsResponse

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
        repositoryTest = CommentRepositoryImpl(dataStore)
    }

    @Test
    fun getCommentsForPostFromAPI() = mainCoroutineRule.runBlockingTest {
        Mockito.`when`(dataStore.getComments(1))
                .thenReturn(commentsData)

        val result = repositoryTest.getComments(1)
        assert(LiveDataTestUtil.getValue(result).status == Status.LOADING)
        assert(LiveDataTestUtil.getValue(result).status == Status.SUCCESS)

        assert(LiveDataTestUtil.getValue(result).data == commentsData)
    }

    @Test(expected = Exception::class)
    fun getCommentsForPostFromAPIThrowsException() = mainCoroutineRule.runBlockingTest {
        Mockito.doThrow(Exception("Comments not found"))
                .`when`(dataStore.getComments(1))
        repositoryTest.getComments(1)
    }

}