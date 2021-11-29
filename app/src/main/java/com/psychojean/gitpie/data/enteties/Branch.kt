package com.psychojean.gitpie.data.enteties

import java.io.Serializable

data class Branch (
    val name: String,
    val commit: Commit
): Serializable

data class Commit(
    val sha: String
)