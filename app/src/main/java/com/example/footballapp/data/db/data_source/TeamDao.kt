package com.example.footballapp.data.db.data_source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.footballapp.data.entity.Team

@Dao
interface TeamDao {

    @Query("SELECT * FROM favourites WHERE id = :id")
    suspend fun getTeamById(id: String): Team?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTeam(team: Team)

    @Query("DELETE FROM favourites WHERE id = :id")
    suspend fun deleteTeamById(id: String)
}
