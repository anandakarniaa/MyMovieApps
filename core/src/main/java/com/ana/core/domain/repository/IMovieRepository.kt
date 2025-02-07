package com.ana.core.domain.repository

import com.ana.core.data.source.Resource
import com.ana.core.domain.model.Movie
import com.ana.core.domain.model.MovieTrailer
import kotlinx.coroutines.flow.Flow

interface IMovieRepository {
    fun getMovie(): Flow<Resource<List<Movie>>>
    fun getMovieTrailerById(id:Int): Flow<Resource<List<MovieTrailer>>>
    suspend fun insertFavoriteToDb(movie:Movie)
    fun getAllFavorite(): Flow<List<Movie>>
    suspend fun deleteFavoriteFromDb(movie: Movie)
    fun isFavorite(id: Int): Flow<Boolean>
}