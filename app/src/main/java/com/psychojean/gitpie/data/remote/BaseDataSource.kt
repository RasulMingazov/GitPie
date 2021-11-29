package com.psychojean.gitpie.data.remote

import android.util.Log
import com.psychojean.gitpie.utils.Resource
import retrofit2.Response

abstract class BaseDataSource {
    protected suspend fun <T> getResult(call: suspend () -> Response<T>): Resource<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Log.d("BaseDataSource", "Success")
                    return Resource.success(body)
                }
            }
            Log.d("BaseDataSource",  " ${response.code()} ${response.message()}")
            return error(" ${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(message: String): Resource<T> {
        Log.d("BaseDataSource","Network call has failed for a following reason: $message" )
        return Resource.error("Network call has failed for a following reason: $message")
    }
}