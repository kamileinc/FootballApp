package com.example.footballapp.view_model

import androidx.lifecycle.*
import com.example.footballapp.data.db.repository.TeamRoomRepository
import com.example.footballapp.data.entity.Team
import com.example.footballapp.utilities.Constants.PARAM_TEAM
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: TeamRoomRepository
) : ViewModel() {

    private val _selectedTeam = MutableLiveData<Team>()
    val selectedTeam: LiveData<Team>
        get() = _selectedTeam

    init {
        savedStateHandle.get<Team>(PARAM_TEAM)?.let { team ->
            viewModelScope.launch {
                val teamInDb = repository.getTeamById(team.id)
                if (teamInDb != null) {
                    _selectedTeam.value = teamInDb!!
                } else {
                    team.favorite = false
                    _selectedTeam.value = team
                }
            }
        }
    }

    fun changeFavoriteStatus() {
        selectedTeam.value.let { updatedTeam ->
            viewModelScope.launch {
                val teamInDb = repository.getTeamById(updatedTeam!!.id)
                if (teamInDb == null) {
                    updatedTeam.favorite = true
                    repository.insertTeam(updatedTeam)
                    _selectedTeam.value = updatedTeam
                } else {
                    updatedTeam.favorite = false
                    repository.deleteTeamById(updatedTeam.id)
                    _selectedTeam.value = updatedTeam
                }
            }
        }
    }
}