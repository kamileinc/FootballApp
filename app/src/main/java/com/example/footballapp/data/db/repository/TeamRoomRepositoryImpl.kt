package com.example.footballapp.data.db.repository

import com.example.footballapp.data.db.data_source.TeamDao
import com.example.footballapp.data.entity.Team

class TeamRoomRepositoryImpl(
    private val dao: TeamDao
) : TeamRoomRepository {

    override suspend fun getTeamById(id: String): Team? {
        return dao.getTeamById(id)
    }

    override suspend fun insertTeam(team: Team) {
        dao.insertTeam(team)
    }

    override suspend fun deleteTeamById(id: String) {
        dao.deleteTeamById(id)
    }
}
