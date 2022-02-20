package com.zntkr.deneme_fly

import androidx.lifecycle.LiveData
import com.zntkr.deneme_fly.model.FlyModel
import com.zntkr.deneme_fly.model.RoomModel
import com.zntkr.deneme_fly.roomdb.InfoDao
import com.zntkr.deneme_fly.service.QApi
import retrofit2.Response
import java.util.*

class Repository(private val dao : InfoDao) {

    // Reservation View Model
    suspend fun addData(roomModel: RoomModel){
        dao.insert(roomModel)
    }

    // My Flights View Model
    val getAll: LiveData<List<RoomModel>> = dao.getAll()
    val getFuture: LiveData<List<RoomModel>> = dao.loadFutureFlights(getNow())
    val getPast: LiveData<List<RoomModel>> = dao.loadPastFlights(getNow())

    suspend fun deleteData(roomModel: RoomModel){
        dao.delete(roomModel)
    }

}

// getting date time for now
fun getNow() : Long {
    val year = Calendar.getInstance().get(Calendar.YEAR)
    var month = Calendar.getInstance().get(Calendar.MONTH)
    if (month < 10){
        month = "0$month".toInt()
    }
    var day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
    if (day < 10){
        day = "0$day".toInt()
    }
    var hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    if (hour < 10){
        hour = "0$hour".toInt()
    }
    var minute = Calendar.getInstance().get(Calendar.MINUTE)
    if (minute < 10){
        minute = "0$minute".toInt()
    }
    var second = Calendar.getInstance().get(Calendar.SECOND)
    if (second < 10){
        second = "0$second".toInt()
    }

    val dateTime = "$year$month$day$hour$minute$second"
    return dateTime.toLong()
}