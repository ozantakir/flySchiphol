package com.zntkr.deneme_fly.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.zntkr.deneme_fly.repository.RoomRepository
import com.zntkr.deneme_fly.model.RoomModel
import com.zntkr.deneme_fly.roomdb.InfoDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ReservationViewModel(app: Application) : AndroidViewModel(app)  {

    private val repo : RoomRepository

    init {
        val db = Room.databaseBuilder(getApplication(),InfoDatabase::class.java,"Information").build()
        val dao = db.infoDao()
        repo = RoomRepository(dao)
    }
    // sending data to room
    fun addData(roomModel: RoomModel){
        viewModelScope.launch(Dispatchers.IO) {
            repo.addData(roomModel)
        }
    }
}