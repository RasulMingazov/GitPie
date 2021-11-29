package com.psychojean.gitpie.data.enteties

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class Repo (
    @PrimaryKey
    val id: Int,
    val branches: Int,
    val description: String?,
    val fork: String,
    val name: String,
    val tags: Int,
    val watchers: Int,
    val language: String?,
    val private: Boolean,
    @SerializedName("default_branch") val defaultBranch: String,
    @SerializedName("stargazers_count") val starredCount: Int,
    @SerializedName("forks_count") val forksCount: Int,
    @SerializedName("watchers_count") val watchersCount: Int,
    @SerializedName("subscribers_count") val subscribersCount: Int,
    @SerializedName("open_issues") val reposCount: Int,
    @SerializedName("html_url") val html: String,
    val owner: Owner

)