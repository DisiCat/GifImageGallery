package com.example.gifimagegallery.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_image")
data class RemoteImage(
    @PrimaryKey val gifId: String,
)
