package com.ana.core.data.source.remote.network

import com.ana.core.data.source.remote.response.GetMovieResponse
import com.ana.core.data.source.remote.response.GetMovieTrailerByIdResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("discover/movie?api_key=$api_key")
    suspend fun getMovie(): GetMovieResponse

    @GET("movie/{id}/videos?api_key=$api_key")
    suspend fun getMovieTrailerById(
        @Path("id") id: Int
    ): GetMovieTrailerByIdResponse

    companion object{
        private const val api_key = "aa6411611d812a07af43acf54ef7787f"
    }
}