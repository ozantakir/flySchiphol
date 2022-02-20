package com.zntkr.deneme_fly.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zntkr.deneme_fly.repository.ApiRepository
import com.zntkr.deneme_fly.model.FlyModel
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val repo = ApiRepository()

    private val _pageLiveData = MutableLiveData<FlyModel>()
    val pageLiveData: LiveData<FlyModel> = _pageLiveData

    fun refreshPage(page: Int,dest: String?,date: String?) {
        viewModelScope.launch {
            val response = repo.getAll(page,dest,date)
            _pageLiveData.postValue(response!!)
        }
    }

}