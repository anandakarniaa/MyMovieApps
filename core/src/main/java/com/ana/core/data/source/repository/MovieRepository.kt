package com.ana.core.data.source.repository

import com.ana.core.data.source.NetworkBoundResource
import com.ana.core.data.source.Resource
import com.ana.core.data.source.local.LocalDataSource
import com.ana.core.data.source.remote.RemoteDataSource
import com.ana.core.data.source.remote.network.ApiResponse
import com.ana.core.data.source.remote.response.GetMovieResponse
import com.ana.core.data.source.remote.response.GetMovieTrailerByIdResponse
import com.ana.core.domain.model.Movie
import com.ana.core.domain.model.MovieTrailer
import com.ana.core.domain.repository.IMovieRepository
import com.ana.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieRepository @Inject constructor(private val remoteDataSource: RemoteDataSource, private val localDataSource: LocalDataSource):
    IMovieRepository {

    override fun getMovie(): Flow<Resource<List<Movie>>> =
        object : NetworkBoundResource<List<Movie>, GetMovieResponse>(){
            override suspend fun createCall(): Flow<ApiResponse<GetMovieResponse>> =
                remoteDataSource.getMovie()

            override fun loadFromNetwork(data: GetMovieResponse): Flow<List<Movie>> =
                DataMapper.mapListResponseToDomain(data.results)
        }.asFlow()

    override fun getMovieTrailerById(id: Int): Flow<Resource<List<MovieTrailer>>> =
        object : NetworkBoundResource<List<MovieTrailer>, GetMovieTrailerByIdResponse>(){
            override suspend fun createCall(): Flow<ApiResponse<GetMovieTrailerByIdResponse>> =
                remoteDataSource.getMovieTrailerById(id)

            override fun loadFromNetwork(data: GetMovieTrailerByIdResponse): Flow<List<MovieTrailer>> =
                DataMapper.mapListMovieTrailerToDomain(data.results)

        }.asFlow()

    override suspend fun insertFavoriteToDb(movie: Movie) =
        localDataSource.insertFavoriteToDb(DataMapper.mapDomainToEntity(movie))

    override fun getAllFavorite(): Flow<List<Movie>> =
        localDataSource.getAllFavorite().map {
            DataMapper.mapListEntityToDomain(it)
        }

    override suspend fun deleteFavoriteFromDb(movie: Movie) =
        localDataSource.deleteFavoriteFromDb(DataMapper.mapDomainToEntity(movie))

    override fun isFavorite(id: Int): Flow<Boolean> =
        localDataSource.isFavorite(id)
}