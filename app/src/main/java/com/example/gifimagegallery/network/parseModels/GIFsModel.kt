package com.example.gifimagegallery.network.parseModels

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "GIFsData")
data class Data(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "url") val images: Images,
    @ColumnInfo(name = "title") val title: String
)
