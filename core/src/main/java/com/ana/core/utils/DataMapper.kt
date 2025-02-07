package com.ana.core.utils

import com.ana.core.data.source.local.entity.FavoriteEntity
import com.ana.core.data.source.remote.response.Result
import com.ana.core.data.source.remote.response.ResultsItem
import com.ana.core.domain.model.Movie
import com.ana.core.domain.model.MovieTrailer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

object DataMapper {
    private const val IMG_PATH = "https://image.tmdb.org/t/p/w500"

    fun mapListResponseToDomain(movieResponse:List<Result>): Flow<List<Movie>> {
        val movieList = ArrayList<Movie>()
        movieResponse.map {
            val movie = Movie(
                IMG_PATH+it.posterPath,
                it.title,
                it.id,
                it.overview,
                it.voteAverage,
                it.voteCount.toDouble(),
                it.runtime,
                it.releaseDate,
                false
            )
            movieList.add(movie)
        }
        return flowOf(movieList)
    }

    fun mapListMovieTrailerToDomain(movieVideoResponse: List<ResultsItem>): Flow<List<MovieTrailer>> {
        val movieVideoList = ArrayList<MovieTrailer>()
        movieVideoResponse.map {
            val movieVideo = MovieTrailer(
                it.iso6391,
                it.iso31661,
                it.name,
                it.key,
                it.site,
                it.size,
                it.type,
                it.official,
                it.publishedAt,
                it.id
            )
            movieVideoList.add(movieVideo)
        }
        return flowOf(movieVideoList)
    }

    fun mapDomainToEntity(movie:Movie) =
        FavoriteEntity(
            movie.id,
            movie.img,
            movie.name,
            movie.overview,
            movie.voteAverage,
            movie.voteCount,
            movie.runtime,
            movie.releaseDate,
            movie.isFavorite
        )
    fun mapListEntityToDomain(listMovieDiscoverEntity: List<FavoriteEntity>): List<Movie> =
        listMovieDiscoverEntity.map {
            Movie(
                it.img,
                it.name,
                it.id,
                it.overview,
                it.voteAverage,
                it.voteCount,
                it.runtime,
                it.releaseDate,
                it.isFavorite
            )
        }

}