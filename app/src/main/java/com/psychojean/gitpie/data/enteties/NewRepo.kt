package com.psychojean.gitpie.data.enteties

import com.google.gson.annotations.SerializedName

data class NewRepo(
    val name: String,
    @SerializedName("private") val isPrivate: Boolean = false,
    val description: String
)