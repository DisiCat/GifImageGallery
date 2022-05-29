package com.example.gifimagegallery.db

import android.content.Context
import androidx.room.*
import com.example.gifimagegallery.db.dao.GifDao
import com.example.gifimagegallery.db.dao.RemoteImageDao
import com.example.gifimagegallery.db.dao.RemoteKeysDao
import com.example.gifimagegallery.network.parseModels.GifModel

@Database(
    entities = [GifModel::class, RemoteKeys::class, RemoteImage::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class GifDatabase : RoomDatabase() {
    abstract fun gifsDao(): GifDao
    abstract fun remoteKeysDao(): RemoteKeysDao
    abstract fun remoteImageDao(): RemoteImageDao

    companion object {
        @Volatile
        private var INSTANCE: GifDatabase? = null

        fun getInstance(context: Context): GifDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext, GifDatabase::class.java, "Gif.db")
                .build()
    }
}