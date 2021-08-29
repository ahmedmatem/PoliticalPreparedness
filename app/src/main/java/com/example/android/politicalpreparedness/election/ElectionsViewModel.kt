package com.example.android.politicalpreparedness.election

import androidx.lifecycle.*
import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.models.Election
import kotlinx.coroutines.launch
import java.lang.Exception

//TODO: Construct ViewModel and provide election datasource
class ElectionsViewModel : ViewModel() {

    init {
        populateUpcomingElections()
    }

    private val _navigateToSelectedElectionDetails = MutableLiveData<Election>()
    val navigateToSelectedElectionDetails: LiveData<Election> = _navigateToSelectedElectionDetails

    //TODO: Create live data val for upcoming elections
    private val _upcomingElectionList = MutableLiveData<List<Election>>()
    val upcomingElectionList: LiveData<List<Election>>
        get() = _upcomingElectionList

    //TODO: Create live data val for saved elections

    //TODO: Create val and functions to populate live data for upcoming elections from the API and saved elections from local database
    private fun populateUpcomingElections() {
        viewModelScope.launch {
            try {
                val response = CivicsApi.retrofitService.getElections()
                _upcomingElectionList.value = response.elections
            } catch (e: Exception) {

            }
        }
    }

    //TODO: Create functions to navigate to saved or upcoming election voter info
    fun displaySelectedElectionInfo(election: Election) {
        _navigateToSelectedElectionDetails.value = election
    }

}