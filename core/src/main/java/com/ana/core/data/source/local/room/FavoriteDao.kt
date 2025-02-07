package com.ana.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.ana.core.data.source.local.entity.FavoriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Insert
    suspend fun insertFavoriteToDb(favoriteEntity: FavoriteEntity)
    @Query("SELECT * FROM tbl_favorite")
    fun getAllFavorite(): Flow<List<FavoriteEntity>>
    @Delete
    suspend fun deleteFavoriteFromDb(favoriteEntity: FavoriteEntity)
    @Query("SELECT EXISTS(SELECT * FROM tbl_favorite WHERE id = :id)")
    fun isFavorite(id:Int): Flow<Boolean>
}