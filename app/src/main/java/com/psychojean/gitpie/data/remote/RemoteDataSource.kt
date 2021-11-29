package com.psychojean.gitpie.data.remote

import android.util.Log
import com.psychojean.gitpie.data.enteties.NewRepo
import retrofit2.http.Body
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val gitService: GitService
) : BaseDataSource() {

    suspend fun getUser(accessToken: String) = getResult {
        gitService.getUser(accessToken)
    }

    suspend fun getAccessToken(
        clientId: String,
        clientSecret: String,
        code: String
    ) = getResult { gitService.getAccessToken(clientId, clientSecret, code) }

    suspend fun getAllRepos(
        accessToken: String
    ) = getResult { gitService.getAllRepos(accessToken) }

    suspend fun getRepo(
        accessToken: String,
        owner: String,
        repoName: String
    ) = getResult { gitService.getRepo(accessToken, owner, repoName) }

    suspend fun getBranches(
        accessToken: String,
        owner: String,
        repoName: String
    ) = getResult { gitService.getBranches(accessToken, owner, repoName) }

    suspend fun getStarred(
        accessToken: String
    ) = getResult { gitService.getStarred(accessToken) }

    suspend fun getOrganizations(
        accessToken: String
    ) = getResult { gitService.getOrganizations(accessToken) }

     suspend fun getFollowing(
        accessToken: String,
        owner: String
    ) = getResult { gitService.getFollowing(accessToken, owner) }

    suspend fun getFollowers(
        accessToken: String,
        owner: String
    ) = getResult { gitService.getFollowers(accessToken, owner) }



    suspend fun deleteRepo(
        accessToken: String,
        owner: String,
        repoName: String
    ) = getResult { gitService.deleteRepo(accessToken, owner, repoName) }

    suspend fun createRepo(
        accessToken: String,
        repo: NewRepo
    ) = getResult { gitService.createRepo(accessToken, repo) }
}