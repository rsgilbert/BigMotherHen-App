package com.monstercode.bigmotherhen.util

import android.content.Context
import androidx.annotation.NonNull
import com.monstercode.bigmotherhen.R

fun getSharedP(@NonNull context: Context)
        = context.getSharedPreferences(
    context.getString(R.string.preferences_filename), Context.MODE_PRIVATE
)

fun saveLastSeenChapter(number: Int, context: Context?) {
    context?.let { nonNullContext: Context ->
        getSharedP(nonNullContext).edit().putInt(nonNullContext.getString(R.string.chapter_last_seen), number).apply()
    }
}

fun isLastSeenChapter(number: Int, context: Context?) : Boolean {
    return context?.let { nonNullContext: Context ->
        val lastSeen: Int = getSharedP(nonNullContext).getInt(nonNullContext.getString(R.string.chapter_last_seen), 1)
        lastSeen == number
    } ?: false
}