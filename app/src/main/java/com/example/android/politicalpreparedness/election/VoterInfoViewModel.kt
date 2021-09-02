package com.example.android.politicalpreparedness.election

import androidx.lifecycle.*
import com.example.android.politicalpreparedness.database.ElectionDao
import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.models.VoterInfoResponse
import kotlinx.coroutines.launch
import java.lang.Exception
import java.lang.IllegalArgumentException

class VoterInfoViewModel(private val dataSource: ElectionDao) : ViewModel() {

    //TODO: Add live data to hold voter info
    private val _voterInfo = MutableLiveData<VoterInfoResponse>()
    val voterInfo: LiveData<VoterInfoResponse> = _voterInfo

    //TODO: Add var and methods to populate voter info
    fun populateVoterInfo(electionId: Int, address: String) {
        viewModelScope.launch {
            try {
                val voterInfoResponse = CivicsApi.retrofitService.getVoterInfo(electionId, address)
                _voterInfo.value = voterInfoResponse
            } catch (e: Exception) {

            }
        }
    }

    //TODO: Add var and methods to support loading URLs

    //TODO: Add var and methods to save and remove elections to local database
    //TODO: cont'd -- Populate initial state of save button to reflect proper action based on election saved status

    /**
     * Hint: The saved state can be accomplished in multiple ways. It is directly related to how elections are saved/removed from the database.
     */

    class Factory(private val dataSource: ElectionDao) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(VoterInfoViewModel::class.java)) {
                return VoterInfoViewModel(dataSource) as T
            }
            throw IllegalArgumentException("Unable to construct ViewModel.")
        }
    }

}