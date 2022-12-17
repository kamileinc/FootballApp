package com.example.footballapp.data.api

import com.example.footballapp.data.entity.TeamsResponse
import com.example.footballapp.utilities.Resource
import javax.inject.Inject

class TeamRepository @Inject constructor(
    private val retroService: RetroService
) : SafeApiCall() {

    suspend fun getTeams(): Resource<TeamsResponse> = safeApiCall {
        retroService.getTeams()
    }
}
