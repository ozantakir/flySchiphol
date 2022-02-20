package com.zntkr.deneme_fly.viewmodel
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.zntkr.deneme_fly.repository.RoomRepository
import com.zntkr.deneme_fly.model.RoomModel
import com.zntkr.deneme_fly.roomdb.InfoDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch



class MyFlightsViewModel(app: Application) : AndroidViewModel(app) {
    private val repo : RoomRepository

    // getting data from room
    val getAll: LiveData<List<RoomModel>>
    val getPast: LiveData<List<RoomModel>>
    val getFuture: LiveData<List<RoomModel>>

    init {
        val db = Room.databaseBuilder(getApplication(), InfoDatabase::class.java, "Information")
            .build()
        val infoDao = db.infoDao()
        repo = RoomRepository(infoDao)
        getAll = repo.getAll
        getPast = repo.getPast
        getFuture = repo.getFuture
    }


    fun deleteAll(){
        viewModelScope.launch(Dispatchers.IO) { repo.deleteAll() }
    }


}