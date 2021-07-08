package com.journey.comments.data.remote.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class CommentResponse : ArrayList<CommentResponseItem>()

data class CommentResponseItem(
        @Expose @SerializedName("body")
        val body: String,
        @Expose @SerializedName("id")
        val id: Int,
        @Expose @SerializedName("email")
        val email: String,
        @Expose @SerializedName("postId")
        val postId: Int,
        @Expose @SerializedName("name")
        val name: String
) : Serializable