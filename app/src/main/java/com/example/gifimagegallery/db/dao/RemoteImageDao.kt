package com.example.gifimagegallery.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.gifimagegallery.db.RemoteImage

@Dao
interface RemoteImageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRemoveImage(gifId : RemoteImage)

    @Query("SELECT * FROM remote_image")
    suspend fun getAllRemoteImageId(): List<RemoteImage>
}