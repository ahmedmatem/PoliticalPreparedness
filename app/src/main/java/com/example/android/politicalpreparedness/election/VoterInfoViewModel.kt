package com.example.android.politicalpreparedness.election

import androidx.lifecycle.*
import androidx.navigation.NavArgsLazy
import com.example.android.politicalpreparedness.database.ElectionDao
import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.models.VoterInfoResponse
import kotlinx.coroutines.launch
import java.lang.Exception
import java.lang.IllegalArgumentException

class VoterInfoViewModel(
    private val navArgs: VoterInfoFragmentArgs,
    private val dataSource: ElectionDao
) : ViewModel() {

    //TODO: Add live data to hold voter info
    private val _voterInfo = MutableLiveData<VoterInfoResponse>()
    val voterInfo: LiveData<VoterInfoResponse> = _voterInfo

    private val _hasVoterInfo = MutableLiveData<Boolean>()
    val hasVoterInfo: LiveData<Boolean> = _hasVoterInfo

    init {
        populateVoterInfo()
    }

    //TODO: Add var and methods to populate voter info
    fun populateVoterInfo() {
        val country = navArgs.argDivision.country
        val state = navArgs.argDivision.state

        if (state.isNotEmpty()) {
            viewModelScope.launch {
                try {
                    val electionId = navArgs.argElectionId
                    val address = "$country,$state"
                    val voterInfoResponse = CivicsApi.retrofitService.getVoterInfo(electionId, address)
                    _voterInfo.value = voterInfoResponse
                    _hasVoterInfo.value = true
                } catch (e: Exception) {
                    _hasVoterInfo.value = false
                }
            }
        } else {
            _hasVoterInfo.value = false
        }
    }

    //TODO: Add var and methods to support loading URLs

    //TODO: Add var and methods to save and remove elections to local database
    //TODO: cont'd -- Populate initial state of save button to reflect proper action based on election saved status

    /**
     * Hint: The saved state can be accomplished in multiple ways. It is directly related to how elections are saved/removed from the database.
     */

    class Factory(
        private val args: VoterInfoFragmentArgs,
        private val dataSource: ElectionDao
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(VoterInfoViewModel::class.java)) {
                return VoterInfoViewModel(args, dataSource) as T
            }
            throw IllegalArgumentException("Unable to construct ViewModel.")
        }
    }

}