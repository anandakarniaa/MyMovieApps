package com.ana.core.data.source.remote

import com.ana.core.data.source.remote.network.ApiResponse
import com.ana.core.data.source.remote.network.ApiService
import com.ana.core.data.source.remote.response.GetMovieResponse
import com.ana.core.data.source.remote.response.GetMovieTrailerByIdResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val apiService: ApiService) {
    suspend fun getMovie(): Flow<ApiResponse<GetMovieResponse>>{
        return flow {
            try {
                val response = apiService.getMovie()
                emit(ApiResponse.Success(response))
            }catch (e: Exception){
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getMovieTrailerById(id:Int): Flow<ApiResponse<GetMovieTrailerByIdResponse>>{
        return channelFlow {
            try {
                val response = apiService.getMovieTrailerById(id)
                trySend(ApiResponse.Success(response))
            }catch (e:Exception){
                trySend(ApiResponse.Error(e.toString()))
            }
            awaitClose()
        }
    }
}