package com.zntkr.deneme_fly.roomdb

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.zntkr.deneme_fly.model.RoomModel

@Dao
interface InfoDao {

    @Query("SELECT * FROM Information")
    fun getAll() : LiveData<List<RoomModel>>

    @Insert
    fun insert(info : RoomModel)

    @Delete
    fun delete(info : RoomModel)

    @Query("SELECT * FROM Information WHERE dateTime > :now")
    fun loadFutureFlights(now: Long): LiveData<List<RoomModel>>

    @Query("SELECT * FROM Information WHERE dateTime < :now")
    fun loadPastFlights(now: Long): LiveData<List<RoomModel>>
}