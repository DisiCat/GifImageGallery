package com.example.gifimagegallery.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.gifimagegallery.network.parseModels.GifModel

@Dao
interface GifDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(gifs: List<GifModel>)

    @Query("SELECT * FROM gifData WHERE title LIKE :searchName")
    fun getGifsBySearch(searchName : String): PagingSource<Int,GifModel>

    @Query("DELETE FROM gifData" )
    suspend fun clearGifs()
}