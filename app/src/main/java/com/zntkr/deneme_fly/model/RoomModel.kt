package com.zntkr.deneme_fly.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Information")
class RoomModel(
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "email")
    var email: String,
    @ColumnInfo(name = "phone")
    var phone: String,
    @ColumnInfo(name = "flightName")
    var flightName: String,
    @ColumnInfo(name = "date")
    var date: String,
    @ColumnInfo(name = "time")
    var time: String,
    @ColumnInfo(name = "seat")
    var seat: String,
    @ColumnInfo(name = "direction")
    var direction: String,
    @ColumnInfo(name = "destination")
    var destination: String,
    @ColumnInfo(name = "dateTime")
    var dateTime: Long
) {
    @PrimaryKey(autoGenerate = true)
    var id = 0
}