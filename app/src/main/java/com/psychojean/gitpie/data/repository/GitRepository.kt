package com.psychojean.gitpie.data.repository

import android.util.Log
import com.psychojean.gitpie.data.enteties.NewRepo
import com.psychojean.gitpie.data.remote.RemoteDataSource
import com.psychojean.gitpie.utils.performGetOperation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GitRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
) {
    fun getUser(accessToken: String) =
        performGetOperation {
            withContext(Dispatchers.IO) {
                remoteDataSource.getUser(accessToken)
            }
        }

    fun getRepo(
        accessToken: String,
        owner: String,
        repoName: String
    ) =
        performGetOperation { remoteDataSource.getRepo(accessToken, owner, repoName) }

    fun getBranches(
        accessToken: String,
        owner: String,
        repoName: String
    ) =
        performGetOperation { remoteDataSource.getBranches(accessToken, owner, repoName) }

    fun getAccessToken(
        clientId: String,
        clientSecret: String,
        code: String
    ) = performGetOperation { remoteDataSource.getAccessToken(clientId, clientSecret, code) }

    fun getAllRepos(accessToken: String) =
        performGetOperation { remoteDataSource.getAllRepos(accessToken) }

    fun getStarred(accessToken: String) =
        performGetOperation { remoteDataSource.getStarred(accessToken) }

    fun getOrganizations(accessToken: String) =
        performGetOperation { remoteDataSource.getOrganizations(accessToken) }

    fun getFollowing(
        accessToken: String,
        owner: String
    ) = performGetOperation { remoteDataSource.getFollowing(accessToken, owner) }

    fun getFollowers(
        accessToken: String,
        owner: String
    ) = performGetOperation { remoteDataSource.getFollowers(accessToken, owner) }

    fun deleteRepo(
        accessToken: String,
        owner: String,
        repoName: String
    ) =
        performGetOperation { remoteDataSource.deleteRepo(accessToken, owner, repoName) }

    fun createRepo(
        accessToken: String,
        repo: NewRepo
    ) =
        performGetOperation { remoteDataSource.createRepo(accessToken, repo) }
}