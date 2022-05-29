package com.example.gifimagegallery.db

import android.content.Context
import androidx.room.*
import com.example.gifimagegallery.network.parseModels.GifModel

@Database(
    entities = [GifModel::class, RemoteKeys::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class GifDatabase : RoomDatabase() {
    abstract fun gifsDao(): GifDao
    abstract fun remoteKeysDao(): RemoteKeysDao

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