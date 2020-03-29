package com.monstercode.bigmotherhen.util

import android.content.Context
import android.view.View
import androidx.annotation.NonNull
import com.monstercode.bigmotherhen.R

fun getSharedP(@NonNull context: Context)
        = context.getSharedPreferences(
    context.getString(R.string.preferences_filename), Context.MODE_PRIVATE
)

fun saveLastSeenChapterNumber(number: Int, context: Context?) {
    context?.let { nonNullContext: Context ->
        getSharedP(nonNullContext).edit().putInt(nonNullContext.getString(R.string.chapter_last_seen), number).apply()
    }
}

fun getLastSeenChapterNumber(context: Context?) : Int {
    return context?.let { nonNullContext: Context ->
        getSharedP(nonNullContext).getInt(nonNullContext.getString(R.string.chapter_last_seen), 1)
    } ?: 1
}

fun isLastSeenChapterNumber(number: Int, context: Context?) : Boolean {
    return context?.let { nonNullContext: Context ->
        getLastSeenChapterNumber(nonNullContext) == number
    } ?: false
}

fun setClickableAnimation(context: Context, view: View) {
    val attrs = intArrayOf(R.attr.selectableItemBackground)
    val typedArray = context.obtainStyledAttributes(attrs)
    val backgroundResource = typedArray.getResourceId(0, 0)
    view.setBackgroundResource(backgroundResource)
    typedArray.recycle()
}