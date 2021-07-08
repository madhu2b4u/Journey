package com.journey.posts

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.journey.LiveDataTestUtil
import com.journey.MainCoroutineRule
import com.journey.TestUtils
import com.journey.common.Status
import com.journey.posts.data.remote.source.PostsRemoteDataSource
import com.journey.posts.data.repository.PostsRepository
import com.journey.posts.data.repository.PostsRepositoryImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations


@ExperimentalCoroutinesApi
class PostsRepositoryTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var repositoryTest: PostsRepository

    @Mock
    lateinit var dataStore: PostsRemoteDataSource

    private val postData = TestUtils.postsResponse

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
        repositoryTest = PostsRepositoryImpl(dataStore)
    }

    @Test
    fun getPostsFromAPI() = mainCoroutineRule.runBlockingTest {
        Mockito.`when`(dataStore.getPosts())
            .thenReturn(postData)

        val result = repositoryTest.getPosts()
        assert(LiveDataTestUtil.getValue(result).status == Status.LOADING)
        assert(LiveDataTestUtil.getValue(result).status == Status.SUCCESS)

        assert(LiveDataTestUtil.getValue(result).data == postData)
    }

    @Test(expected = Exception::class)
    fun getPostsFromAPIThrowsException() = mainCoroutineRule.runBlockingTest {
        Mockito.doThrow(Exception("Posts not found"))
            .`when`(dataStore.getPosts())
        repositoryTest.getPosts()
    }

}