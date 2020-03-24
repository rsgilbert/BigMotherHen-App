package com.monstercode.bigmotherhen.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.monstercode.bigmotherhen.databinding.ListItemBinding
import com.monstercode.bigmotherhen.domain.Chapter
import timber.log.Timber

class ListChapterAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<Chapter, ListChapterAdapter.ViewHolder>(ChapterDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val chapter: Chapter = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(chapter)
        }
        holder.bind(chapter)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(private val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Chapter) {
            binding.chapter = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    class OnClickListener(val clickListener: (chapter: Chapter) -> Unit) {
        fun onClick(chapter: Chapter) = clickListener(chapter)
    }

}

class ChapterDiffCallback : DiffUtil.ItemCallback<Chapter>() {
    override fun areItemsTheSame(oldItem: Chapter, newItem: Chapter): Boolean {
        return oldItem.number == newItem.number
    }
    override fun areContentsTheSame(oldItem: Chapter, newItem: Chapter): Boolean {
        return oldItem == newItem
    }
}
