package com.journey.comments.presentation.viewmodel

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.journey.comments.data.remote.models.CommentResponse
import com.journey.comments.domain.CommentUseCase
import com.journey.common.Event
import com.journey.common.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommentViewModel @Inject constructor(
        private val mCommentUseCase: CommentUseCase
) : ViewModel() {

    val commentResult = MediatorLiveData<Event<Result<CommentResponse>>>()

    fun fetchCommentsToPost(postId: Int) {
        viewModelScope.launch {
            commentResult.addSource(mCommentUseCase.getComments(postId)) {
                commentResult.value = Event(it)
            }
        }
    }

}