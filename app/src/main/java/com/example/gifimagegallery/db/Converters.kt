package com.example.gifimagegallery.db

import androidx.room.TypeConverter
import com.example.gifimagegallery.network.parseModels.Images
import com.google.gson.Gson
import java.lang.ProcessBuilder.Redirect.to

class Converters {
    @TypeConverter
    fun imagesToUrl(images: Images?): String? {
        return Gson().toJson(images)
    }

    @TypeConverter
    fun urlToImages(json: String?): Images? {
        return Gson().fromJson(json, Images::class.java)
    }
}