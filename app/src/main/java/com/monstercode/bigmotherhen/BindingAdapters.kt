package com.monstercode.bigmotherhen

import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.monstercode.bigmotherhen.domain.Chapter
import com.monstercode.bigmotherhen.list.ListChapterAdapter
import com.monstercode.bigmotherhen.util.getLastSeenChapterNumber
import com.monstercode.bigmotherhen.util.isLastSeenChapterNumber
import com.monstercode.bigmotherhen.util.setClickableAnimation
import de.hdodenhof.circleimageview.CircleImageView
import timber.log.Timber

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
    (adapter as ListChapterAdapter).submitList(data)
}

@BindingAdapter("scrollToLastSeenChapter")
fun RecyclerView.scrollToLastSeenChapter(doScroll: Boolean?) {
    doScroll?.let {
        if (it) {
            post {
                Timber.i("Last seen position is ${getLastSeenChapterNumber(context)}")
                smoothScrollToPosition(getLastSeenChapterNumber(context))
            }
        }
    }
}

@BindingAdapter("bookmarkLastSeenChapter")
fun ImageView.bookmarkLastSeenChapter(chapter: Chapter) {
    visibility = if (isLastSeenChapterNumber(chapter.number, context)) {
        View.VISIBLE
    } else View.GONE
}

@BindingAdapter("addClickAnimation")
fun RelativeLayout.addClickAnimation(shouldAdd: Boolean?) {
    shouldAdd?.let {
        if (it) {
            setClickableAnimation(context = context, view = this)
        }
    }
}

@BindingAdapter("addDivider")
fun RecyclerView.addDivider(shouldAdd: Boolean?) {
    shouldAdd?.let {
        if(it) {
            val itemDec = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            itemDec.setDrawable(ContextCompat.getDrawable(context, R.drawable.divider)!!)
            addItemDecoration(itemDec)
        }
    }
}