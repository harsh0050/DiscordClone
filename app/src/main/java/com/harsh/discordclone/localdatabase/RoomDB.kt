package com.harsh.discordclone.localdatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.harsh.discordclone.localdatabase.dao.CountryCodeDao
import com.harsh.discordclone.model.CountryCode


@Database(entities = [CountryCode::class], version = 1, exportSchema = true)
abstract class RoomDB : RoomDatabase() {
    abstract fun getCountryCodeDao(): CountryCodeDao
    companion object {
        private var INSTANCE: RoomDB? = null
        fun getInstance(context: Context): RoomDB = synchronized(this) {
            if (INSTANCE != null) {
                INSTANCE!!
            } else {
                INSTANCE = Room.databaseBuilder(context, RoomDB::class.java, "database.db")
                    .createFromAsset("discord_room_database.db")
                    .fallbackToDestructiveMigrationFrom()
                    .build()
                INSTANCE!!
            }
        }
    }
}