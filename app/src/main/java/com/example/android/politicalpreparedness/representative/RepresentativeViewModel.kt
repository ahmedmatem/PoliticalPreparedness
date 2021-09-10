package com.example.android.politicalpreparedness.representative

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.*
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.CivicsApiService
import com.example.android.politicalpreparedness.network.models.Address
import com.example.android.politicalpreparedness.representative.model.Representative
import kotlinx.coroutines.launch

class RepresentativeViewModel: ViewModel() {

    private val apiService = CivicsApi.retrofitService

    //TODO: Establish live data for representatives and address
    private val _representatives = MutableLiveData<List<Representative>>()
    val representatives: LiveData<List<Representative>> = _representatives

    private val _address = MutableLiveData<Address>()
    val address: LiveData<Address> = _address

    init {
        _address.value = Address("", "", "", "", "")
    }

    //TODO: Create function to fetch representatives from API from a provided address

    fun getRepresentatives() {
        viewModelScope.launch {
            address.value?.let {
                val (offices, officials) = apiService.getRepresentatives(it.toFormattedString())
                _representatives.value = offices.flatMap { office ->  office.getRepresentatives(officials)}
            }
        }
    }

    /**
     *  The following code will prove helpful in constructing a representative from the API. This code combines the two nodes of the RepresentativeResponse into a single official :

    val (offices, officials) = getRepresentativesDeferred.await()
    _representatives.value = offices.flatMap { office -> office.getRepresentatives(officials) }

    Note: getRepresentatives in the above code represents the method used to fetch data from the API
    Note: _representatives in the above code represents the established mutable live data housing representatives

     */

    //TODO: Create function get address from geo location
    fun updateAddress(address: Address){
        _address.value = address
        getRepresentatives() // populate representative list
    }

    //TODO: Create function to get address from individual fields

}
