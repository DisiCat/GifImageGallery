package com.example.gifimagegallery.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKeys(
    @PrimaryKey val gifId: String,
    val prevKey: Int?,
    val nextKey: Int?
)
