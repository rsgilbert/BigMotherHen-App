package com.monstercode.bigmotherhen.chapter

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.monstercode.bigmotherhen.domain.Chapter
import com.monstercode.bigmotherhen.repository.ChapterRepository

class ChapterViewModel (chapterNumber: Int, repository: ChapterRepository) : ViewModel() {

    val chapter : LiveData<Chapter> = repository.getChapter(chapterNumber)

}