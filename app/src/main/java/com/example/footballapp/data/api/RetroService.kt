package com.example.footballapp.data.api

import com.example.footballapp.data.entity.TeamsResponse
import retrofit2.http.GET

interface RetroService {

    @GET("teams.json")
    suspend fun getTeams(): TeamsResponse
}
