package com.zntkr.deneme_fly.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.zntkr.deneme_fly.repository.RoomRepository
import com.zntkr.deneme_fly.roomdb.InfoDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MyFlightsDetailsViewModel(app: Application) : AndroidViewModel(app) {

    private val repo : RoomRepository

    init {
        val db = Room.databaseBuilder(getApplication(), InfoDatabase::class.java,"Information").build()
        val dao = db.infoDao()
        repo = RoomRepository(dao)
    }

    fun deleteData(flightName: String){
        viewModelScope.launch(Dispatchers.IO) {
            repo.deleteData(flightName)
        }
    }
}