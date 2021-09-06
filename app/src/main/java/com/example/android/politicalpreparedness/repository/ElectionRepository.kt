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

    val elections: LiveData<List<Election>> = database.electionDao.getAll()
    val followedElections: LiveData<List<Election>> = database.electionDao.getFollowedElections()

    suspend fun refreshElections() {
        withContext(Dispatchers.IO) {
            val elections = apiService.getElections().elections.toTypedArray()
            database.electionDao.insertElection(*elections)
        }
    }

    suspend fun getVoterInfo(electionId: Int, address: String): VoterInfoResponse {
        return withContext(Dispatchers.IO) {
            apiService.getVoterInfo(electionId, address)
        }
    }

    suspend fun followElection(electionId: Int) {
        withContext(Dispatchers.IO) {
            database.electionDao.followElection(electionId)
        }
    }

    suspend fun unfollowElection(electionId: Int) {
        withContext(Dispatchers.IO) {
            database.electionDao.unfollowElection(electionId)
        }
    }

    suspend fun deleteElection(election: Election) {
        withContext(Dispatchers.IO) {
            database.electionDao.delete(election)
        }
    }

    suspend fun isElectionFollowed(electionId: Int): Boolean {
        return withContext(Dispatchers.IO) {
            database.electionDao.isElectionFollowed(electionId)
        }
    }
}