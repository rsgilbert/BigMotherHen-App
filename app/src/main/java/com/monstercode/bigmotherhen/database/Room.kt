package com.monstercode.bigmotherhen.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DatabaseChapter::class], version = 1)
abstract class ChapterDatabase: RoomDatabase() {
    abstract val chapterDao: ChapterDao
}

private lateinit var INSTANCE: ChapterDatabase

fun getDatabase(context: Context): ChapterDatabase {
    synchronized(ChapterDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                ChapterDatabase::class.java,
                "ChapterDatabase").build()
        }
    }
    return INSTANCE
}