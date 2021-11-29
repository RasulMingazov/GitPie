package com.psychojean.gitpie.data.remote

import com.psychojean.gitpie.data.enteties.*
import retrofit2.Response
import retrofit2.http.*


interface GitService {

    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("https://github.com/login/oauth/access_token")
    suspend fun getAccessToken(
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
        @Field("code") code: String,
    ): Response<AccessToken>

    @Headers("Accept: application/vnd.github.v3+json")
    @GET("/user")
    suspend fun getUser(@Header("Authorization") auth: String): Response<User>

    @GET("/repos/{owner}/{repo}")
    @Headers("Accept: application/vnd.github.v3+json")
    suspend fun getRepo(
        @Header("Authorization") accessToken: String,
        @Path("owner") owner: String,
        @Path("repo") repoName: String
    ): Response<Repo>

    @GET("/repos/{owner}/{repo}/branches")
    @Headers("Accept: application/vnd.github.v3+json")
    suspend fun getBranches(
        @Header("Authorization") accessToken: String,
        @Path("owner") owner: String,
        @Path("repo") repoName: String
    ): Response<List<Branch>>

    @GET("/user/repos")
    suspend fun getAllRepos(
        @Header("Authorization") accessToken: String
    ): Response<List<Repo>>

    @GET("user/starred")
    suspend fun getStarred(
        @Header("Authorization") accessToken: String
    ): Response<List<Repo>>

    @GET("user/orgs")
    suspend fun getOrganizations(
        @Header("Authorization") accessToken: String
    ): Response<List<Organization>>

    @GET("/users/{owner}/following")
    suspend fun getFollowing(
        @Header("Authorization") accessToken: String,
        @Path("owner") owner: String,
    ): Response<List<Follow>>

    @GET("/users/{owner}/followers")
    suspend fun getFollowers(
        @Header("Authorization") accessToken: String,
        @Path("owner") owner: String,
    ): Response<List<Follow>>

    /*
    DELETE functions
     */
    @DELETE("/repos/{owner}/{repo}")
    @Headers("Accept: application/vnd.github.v3+json")
    suspend fun deleteRepo(
        @Header("Authorization") accessToken: String,
        @Path("owner") owner: String,
        @Path("repo") repoName: String
    ): Response<Void>

    /*
    POST functions
   */
    @POST("/user/repos")
    @Headers("Accept: application/vnd.github.v3+json")
    suspend fun createRepo(
        @Header("Authorization") accessToken: String,
        @Body repo: NewRepo
    ): Response<Repo>
}
