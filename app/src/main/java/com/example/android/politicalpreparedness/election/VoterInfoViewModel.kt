package com.example.android.politicalpreparedness.election

import android.content.Context
import androidx.lifecycle.*
import com.example.android.politicalpreparedness.database.ElectionDatabase
import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.models.VoterInfoResponse
import com.example.android.politicalpreparedness.repository.ElectionRepository
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class VoterInfoViewModel(
    private val navArgs: VoterInfoFragmentArgs,
    private val context: Context
) : ViewModel() {

    private val database = ElectionDatabase.getInstance(context)
    private val repository = ElectionRepository(database)

    //TODO: Add live data to hold voter info
    private val _voterInfo = MutableLiveData<VoterInfoResponse>()
    val voterInfo: LiveData<VoterInfoResponse> = _voterInfo

    private val _hasVoterInfo = MutableLiveData<Boolean>()
    val hasVoterInfo: LiveData<Boolean> = _hasVoterInfo

    private val _votingLocationsUrl = MutableLiveData<String>()
    val votingLocationsUrl: LiveData<String> = _votingLocationsUrl

    private val _ballotInformationUrl = MutableLiveData<String>()
    val ballotInformationUrl: LiveData<String> = _ballotInformationUrl

    private val _correspondenceAddress = MutableLiveData<String>()
    val correspondenceAddress: LiveData<String> = _correspondenceAddress

    private val _hasCorrespondenceAddress = MutableLiveData<Boolean>()
    val hasCorrespondenceAddress: LiveData<Boolean> = _hasCorrespondenceAddress

    private val _isElectionFollowed = MutableLiveData<Boolean>()
    val isElectionFollowed: LiveData<Boolean> = _isElectionFollowed

    init {
        populateVoterInfo()
        checkFollowingElection()
    }

    //TODO: Add var and methods to populate voter info
    private fun populateVoterInfo() {
        val country = navArgs.argDivision.country
        val state = navArgs.argDivision.state

        if (state.isNotEmpty()) {
            viewModelScope.launch {
                val electionId = navArgs.argElectionId
                val address = "$country,$state"
                _voterInfo.value = repository.getVoterInfo(electionId, address)
                getCorrespondenceAddress()
                _hasVoterInfo.value = true
            }
        } else {
            _hasVoterInfo.value = false
        }
    }

    //TODO: Add var and methods to support loading URLs
    fun loadVotingLocations() {
        _votingLocationsUrl.value =
            _voterInfo.value?.state?.get(0)?.electionAdministrationBody?.electionInfoUrl
    }

    fun loadBallotInformation() {
        _ballotInformationUrl.value =
            _voterInfo.value?.state?.get(0)?.electionAdministrationBody?.ballotInfoUrl
    }

    private fun getCorrespondenceAddress() {
        _correspondenceAddress.value =
            _voterInfo.value?.state?.get(0)?.electionAdministrationBody?.correspondenceAddress?.toFormattedString()
        _hasCorrespondenceAddress.value = _correspondenceAddress.value != null
    }

    //TODO: Add var and methods to save and remove elections to local database
    //TODO: cont'd -- Populate initial state of save button to reflect proper action based on election saved status

    /**
     * Hint: The saved state can be accomplished in multiple ways. It is directly related to how elections are saved/removed from the database.
     */

    private fun checkFollowingElection() {
        viewModelScope.launch {
            _isElectionFollowed.value = repository.isElectionFollowed(navArgs.argElectionId)
        }
    }

    fun toggleFollowElection() {
        viewModelScope.launch {
            when (_isElectionFollowed.value) {
                true -> repository.unfollowElection(navArgs.argElectionId)
                false -> repository.followElection(navArgs.argElectionId)
            }
            _isElectionFollowed.value = !_isElectionFollowed.value!!
        }
    }

    class Factory(
        private val args: VoterInfoFragmentArgs,
        private val context: Context
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(VoterInfoViewModel::class.java)) {
                return VoterInfoViewModel(args, context) as T
            }
            throw IllegalArgumentException("Unable to construct ViewModel.")
        }
    }

}