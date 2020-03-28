package com.monstercode.bigmotherhen.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ChapterDao {
    @get:Query("SELECT * FROM DatabaseChapter")
    val chapterListLiveData : LiveData<List<DatabaseChapter>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(chapters: List<DatabaseChapter>)

    @Query("SELECT * FROM DatabaseChapter WHERE number = :number LIMIT 1")
    fun getChapter(number: Int) : LiveData<DatabaseChapter?>


    @Query("SELECT * FROM DatabaseChapter")
    suspend fun chapters() : List<DatabaseChapter>

}