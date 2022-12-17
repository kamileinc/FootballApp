package com.example.footballapp.data.db.repository

import com.example.footballapp.data.entity.Team

interface TeamRoomRepository {

    suspend fun getTeamById(id: String): Team?

    suspend fun insertTeam(team: Team)

    suspend fun deleteTeamById(id: String)
}
