package com.zntkr.deneme_fly.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zntkr.deneme_fly.model.RoomModel

@Database(entities = [RoomModel::class], version = 1)
abstract class InfoDatabase : RoomDatabase() {
    abstract fun infoDao() : InfoDao
}