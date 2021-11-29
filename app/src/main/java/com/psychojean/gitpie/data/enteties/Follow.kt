package com.psychojean.gitpie.data.enteties

import com.google.gson.annotations.SerializedName

data class Follow(
    val login: String,
    @SerializedName("avatar_url") val avatar: String,)