package com.zntkr.deneme_fly.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zntkr.deneme_fly.model.Destination
import com.zntkr.deneme_fly.model.DestinationsList
import com.zntkr.deneme_fly.repository.ApiRepository
import com.zntkr.deneme_fly.model.FlyModel
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {
    private val repo = ApiRepository()

    private val _pageLiveData = MutableLiveData<FlyModel>()
    val pageLiveData: LiveData<FlyModel> = _pageLiveData

    private val _destinationLive = MutableLiveData<Destination>()
    val destinaitonLive: LiveData<Destination> = _destinationLive

    var number = 0
    fun addNumber(){
            number++
    }
    fun subNumber(){
        if(number > 0){
            number--
        }

    }

    fun refreshPage(dest: String?,date: String?) {
        viewModelScope.launch {
            val response = repo.getAll(number,dest,date)
            _pageLiveData.postValue(response!!)
        }
    }

    fun getDest(code: String?){
        viewModelScope.launch {
            val resp = repo.getDestinations(code)
            _destinationLive.postValue(resp!!)
        }
    }

}