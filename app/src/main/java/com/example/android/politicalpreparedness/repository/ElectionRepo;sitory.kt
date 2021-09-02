package com.example.android.politicalpreparedness.repository

import androidx.lifecycle.LiveData
import com.example.android.politicalpreparedness.database.ElectionDatabase
import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.CivicsApiService
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.network.models.VoterInfoResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ElectionRepository(private val database: ElectionDatabase) {
    private val apiService: CivicsApiService = CivicsApi.retrofitService

    lateinit var voterInfo: VoterInfoResponse

    val elections: LiveData<List<Election>> = database.electionDao.getAll()
    val followedElections: LiveData<List<Election>> = database.electionDao.getFollowed()

    suspend fun refreshElections() {
        withContext(Dispatchers.IO) {
            val elections = apiService.getElections().elections.toTypedArray()
            database.electionDao.insertElection(*elections)
        }
    }

    suspend fun getVoterInfo(electionId: Int, address: String) {
        withContext(Dispatchers.IO) {
            voterInfo = apiService.getVoterInfo(electionId, address)
        }
    }

    suspend fun saveElection(election: Election) {
        withContext(Dispatchers.IO) {
            database.electionDao.insertElection(election)
        }
    }

    suspend fun deleteElection(election: Election) {
        withContext(Dispatchers.IO) {
            database.electionDao.delete(election)
        }
    }
}