package com.zntkr.deneme_fly.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.zntkr.deneme_fly.Repository
import com.zntkr.deneme_fly.model.RoomModel
import com.zntkr.deneme_fly.roomdb.InfoDao
import com.zntkr.deneme_fly.roomdb.InfoDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

import java.util.*


class MyFlightsViewModel(app: Application) : AndroidViewModel(app) {
    private val repo : Repository
    val getAll: LiveData<List<RoomModel>>
    val getPast: LiveData<List<RoomModel>>
    val getFuture: LiveData<List<RoomModel>>

    init {
        val db = Room.databaseBuilder(getApplication(), InfoDatabase::class.java, "Information")
            .build()
        val infoDao = db.infoDao()
        repo = Repository(infoDao)
        getAll = repo.getAll
        getPast = repo.getPast
        getFuture = repo.getFuture
    }

    fun deleteData(roomModel: RoomModel){
        viewModelScope.launch(Dispatchers.IO) { repo.deleteData(roomModel) }
    }


}