package com.wychlw.work1.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ProjDb::class, ProjColDb::class, ProjItemDb::class, ProjItemTimelineDb::class], version =2)
abstract class ProjDatabase : RoomDatabase() {
    abstract fun projDao(): ProjDao

    companion object {
        @Volatile
        private var instance: ProjDatabase? = null

        fun getInstance(context: Context): ProjDatabase {

            return instance ?: synchronized(this) {
                println("Building database...")
                Room.databaseBuilder(
                    context,
                    ProjDatabase::class.java,
                    "proj.db"
                ).build().also {
                    println("Build fin")
                    instance = it
                }
            }
        }
    }
}