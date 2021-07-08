package com.journey.posts.data.remote.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class PostsResponse : ArrayList<Post>()

data class Post(
    @Expose @SerializedName("body")
    val body: String,
    @Expose @SerializedName("id")
    val id: Int,
    @Expose @SerializedName("title")
    val title: String,
    @Expose @SerializedName("userId")
    val userId: Int
) : Serializable