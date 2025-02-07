package com.ana.core.domain.usecase

import com.ana.core.data.source.Resource
import com.ana.core.domain.model.Movie
import com.ana.core.domain.model.MovieTrailer
import kotlinx.coroutines.flow.Flow

interface MovieUseCase {
    fun getMovie(): Flow<Resource<List<Movie>>>
    fun getMovieTrailerById(id:Int): Flow<Resource<List<MovieTrailer>>>
    suspend fun insertFavortieToDb(movie:Movie)
    fun getAllFavorite(): Flow<List<Movie>>
    suspend fun deleteFavoriteFromDb(movie: Movie)
    fun isFavorite(id:Int): Flow<Boolean>
}