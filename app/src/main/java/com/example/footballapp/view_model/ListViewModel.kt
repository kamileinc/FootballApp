package com.example.footballapp.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.footballapp.data.api.TeamRepository
import com.example.footballapp.data.entity.Team
import com.example.footballapp.utilities.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val teamRepository: TeamRepository
) : ViewModel() {

    private val _teams = MutableLiveData<Resource<List<Team>>>(Resource.Empty())
    val teams: LiveData<Resource<List<Team>>>
        get() = _teams

    init {
        getTeams()
    }

    private fun getTeams() {
        viewModelScope.launch(Dispatchers.IO) {
            _teams.postValue(Resource.Loading())
            when (val resource = teamRepository.getTeams()) {
                is Resource.Success -> {
                    resource.data?.let {
                        _teams.postValue(Resource.Success(resource.data.teams))
                    }
                }
                is Resource.Failure -> {
                    _teams.postValue(
                        resource.message?.let { errorMessage ->
                            Resource.Failure(
                                errorMessage
                            )
                        }
                    )
                }
                else -> Unit
            }
        }
    }
}
