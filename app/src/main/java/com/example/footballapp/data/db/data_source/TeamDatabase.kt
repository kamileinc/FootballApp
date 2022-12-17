package com.example.footballapp.data.db.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.footballapp.data.entity.Team

@Database(
    entities = [Team::class],
    version = 1,
    exportSchema = false
)
abstract class TeamDatabase : RoomDatabase() {

    abstract val teamDao: TeamDao

    companion object {
        const val DATABASE_NAME = "teams_db"
    }
}
