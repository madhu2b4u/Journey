package com.journey.comments.domain

import com.journey.comments.data.repository.CommentRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CommentUseCaseImpl @Inject constructor(private val mCommentRepository: CommentRepository) :
        CommentUseCase {
    override suspend fun getComments(postId: Int) = mCommentRepository.getComments(postId)

}
