package com.example.myshows.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.myshows.Movie


@Database(entities = [Movie::class], version=1)
@TypeConverters(MovieTypeConverters::class)
abstract class MovieDatabase: RoomDatabase() {
    abstract fun movieDao():MovieDao
}
