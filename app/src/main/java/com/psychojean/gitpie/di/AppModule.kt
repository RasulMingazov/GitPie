package com.psychojean.gitpie.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.psychojean.gitpie.data.remote.GitService
import com.psychojean.gitpie.data.remote.RemoteDataSource
import com.psychojean.gitpie.data.repository.GitRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Provides
    fun provideOkHttpClient() = OkHttpClient.Builder().
    addInterceptor(HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }).build()


        @Singleton
    @Provides
    fun provideRetrofitApi(client: OkHttpClient, gson: Gson): Retrofit = Retrofit.Builder()
        .baseUrl("https://api.github.com")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideGitService(retrofit: Retrofit): GitService = retrofit.create(GitService::class.java)

    @Singleton
    @Provides
    fun provideRemoteDataSource(gitService: GitService) = RemoteDataSource(gitService)


    @Singleton
//    @Provides
//    fun provideDatabase(@ApplicationContext appContext: Context) = AppDatabase.getDatabase(appContext)
//
//    @Singleton
//    @Provides
//    fun provideCharacterDao(db: AppDatabase) = db.countryDao()

//    @Singleton
    @Provides
    fun provideRepository(remoteDataSource: RemoteDataSource

    ) =
        GitRepository(remoteDataSource)
}