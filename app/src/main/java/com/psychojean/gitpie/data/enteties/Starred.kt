package com.psychojean.gitpie.data.enteties

import com.google.gson.annotations.SerializedName

data class Starred (
    val id: Int,
    val name: String,
    @SerializedName("private") val isPrivate: Boolean,
    val owner: Owner,
    val description: String,
    val language: String,
    val visibility: String
)