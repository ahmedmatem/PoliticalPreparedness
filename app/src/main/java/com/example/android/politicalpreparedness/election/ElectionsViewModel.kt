package com.example.android.politicalpreparedness.election

import android.content.Context
import androidx.lifecycle.*
import com.example.android.politicalpreparedness.database.ElectionDatabase
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.repository.ElectionRepository
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

//TODO: Construct ViewModel and provide election datasource
class ElectionsViewModel(context: Context) : ViewModel() {

    private val database: ElectionDatabase = ElectionDatabase.getInstance(context)
    private val repository: ElectionRepository = ElectionRepository(database)

    init {
        viewModelScope.launch {
            repository.refreshElections()
        }
    }

    private val _navigateToVoterInfo = MutableLiveData<Election>()
    val navigateToVoterInfo: LiveData<Election> = _navigateToVoterInfo

    //TODO: Create live data val for upcoming elections

    //TODO: Create live data val for saved elections

    //TODO: Create val and functions to populate live data for upcoming elections from the API and saved elections from local database

    //TODO: Create functions to navigate to saved or upcoming election voter info

    val upcomingElections = repository.elections
    val followedElections = repository.followedElections

    fun displayVoterInfo(election: Election) {
        _navigateToVoterInfo.value = election
    }

    class Factory(private val context: Context) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ElectionsViewModel::class.java)) {
                return ElectionsViewModel(context) as T
            }
            throw IllegalArgumentException("Unable to construct ViewModel.")
        }

    }

}