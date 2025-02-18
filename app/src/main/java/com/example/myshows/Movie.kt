package com.example.myshows
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "movie")
data class Movie(
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    var title: String = "",
    var image: String = "",
    var description: String = "",
    var status: Int = 0
)