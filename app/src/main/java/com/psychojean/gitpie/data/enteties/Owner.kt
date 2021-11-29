package com.psychojean.gitpie.data.enteties

import com.google.gson.annotations.SerializedName

data class Owner (
    val id: Int,
    @SerializedName("avatar_url") val avatar: String,
    val login: String
    )