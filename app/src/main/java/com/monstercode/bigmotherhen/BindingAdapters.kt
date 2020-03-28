package com.monstercode.bigmotherhen

import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.monstercode.bigmotherhen.domain.Chapter
import com.monstercode.bigmotherhen.list.ListChapterAdapter
import com.monstercode.bigmotherhen.util.isLastSeenChapter
import de.hdodenhof.circleimageview.CircleImageView
import timber.log.Timber
import java.lang.Exception

@BindingAdapter("imageUrl")
fun ImageView.bindImage(imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(context)
            .load(imgUri)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image)
            )
            .into(this)
    }
}


@BindingAdapter("imageUrl")
fun CircleImageView.bindImage(imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(context)
            .load(imgUri)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image)
            )
            .into(this)
    }
}


@BindingAdapter("listData")
fun RecyclerView.bindRecyclerView(data: List<Chapter>?) {
    Timber.i("Binding data size: ${data?.size}")
    (adapter as ListChapterAdapter).submitList(data)

}

@BindingAdapter("bookmarkLastSeenChapter")
fun ImageView.bookmarkLastSeenChapter(chapter: Chapter) {
    visibility = if (isLastSeenChapter(chapter.number, context)) {
        Timber.i("This chapter of number: ${chapter.number} is the last seen")
        View.VISIBLE
    } else View.GONE
}