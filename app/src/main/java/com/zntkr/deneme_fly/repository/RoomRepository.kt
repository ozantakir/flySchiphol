package com.zntkr.deneme_fly.repository

import androidx.lifecycle.LiveData
import com.zntkr.deneme_fly.model.RoomModel
import com.zntkr.deneme_fly.roomdb.InfoDao
import java.util.*

// repository for room database
class RoomRepository(private val dao : InfoDao) {

    // Reservation View Model
    suspend fun addData(roomModel: RoomModel){
        dao.insert(roomModel)
    }
    suspend fun deleteData(flightName: String){
        dao.delete(flightName)
    }

    // My Flights View Model
    val getAll: LiveData<List<RoomModel>> = dao.getAll()
    val getFuture: LiveData<List<RoomModel>> = dao.loadFutureFlights(getNow())
    val getPast: LiveData<List<RoomModel>> = dao.loadPastFlights(getNow())

    suspend fun deleteAll(){
        dao.deleteAll()
    }
}

// getting date time for now
fun getNow() : Long {
    var month : String? = null
    var day : String? = null
    var hour : String? = null
    var minute : String? = null
    var second : String? = null
    val year = Calendar.getInstance().get(Calendar.YEAR)
    month = (Calendar.getInstance().get(Calendar.MONTH) + 1).toString()
    if (month.toInt() < 10){
        month = "0$month"
    }
    day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH).toString()
    if (day.toInt() < 10){
        day = "0$day"
    }
    hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY).toString()
    if (hour.toInt() < 10){
        hour = "0$hour"
    }
    minute = Calendar.getInstance().get(Calendar.MINUTE).toString()
    if (minute.toInt() < 10){
        minute = "0$minute"
    }
    second = Calendar.getInstance().get(Calendar.SECOND).toString()
    if (second.toInt() < 10){
        second = "0$second"
    }

    val dateTime = "$year$month$day$hour$minute$second"
    return dateTime.toLong()
}