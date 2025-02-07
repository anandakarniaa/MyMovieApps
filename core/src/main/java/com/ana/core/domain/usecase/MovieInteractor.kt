package com.ana.core.domain.usecase

import com.ana.core.data.source.Resource
import com.ana.core.domain.model.Movie
import com.ana.core.domain.model.MovieTrailer
import com.ana.core.domain.repository.IMovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieInteractor @Inject constructor(private val iMovieRepository: IMovieRepository): MovieUseCase {
    override fun getMovie(): Flow<Resource<List<Movie>>> =
        iMovieRepository.getMovie()

    override fun getMovieTrailerById(id: Int): Flow<Resource<List<MovieTrailer>>> =
        iMovieRepository.getMovieTrailerById(id)

    override suspend fun insertFavortieToDb(movie: Movie) =
        iMovieRepository.insertFavoriteToDb(movie)

    override fun getAllFavorite(): Flow<List<Movie>> =
        iMovieRepository.getAllFavorite()

    override suspend fun deleteFavoriteFromDb(movie: Movie) =
        iMovieRepository.deleteFavoriteFromDb(movie)

    override fun isFavorite(id: Int): Flow<Boolean> =
        iMovieRepository.isFavorite(id)
}