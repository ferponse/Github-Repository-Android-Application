package com.technical_challenge.github.ui.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.technical_challenge.github.databinding.GithubRepositoryItemBinding
import com.technical_challenge.github.ui.model.GithubRepositoryUIModel

class RepositoryAdapter(
    private val listener: OnItemClickListener
) : PagingDataAdapter<GithubRepositoryUIModel, RepositoryAdapter.ViewHolder>(
    diffCallback = GITHUB_REPOSITORY_COMPARATOR
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            GithubRepositoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    inner class ViewHolder(private val binding: GithubRepositoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    if (item != null) {
                        listener.onItemClick(item)
                    }
                }
            }
        }

        fun bind(githubRepository: GithubRepositoryUIModel) {
            Glide.with(binding.imageView)
                .load("https://play-lh.googleusercontent.com/PCpXdqvUWfCW1mXhH1Y_98yBpgsWxuTSTofy3NGMo9yBTATDyzVkqU580bfSln50bFU")
                .into(binding.imageView)
            binding.githubRepository = githubRepository
        }

    }

    interface OnItemClickListener {
        fun onItemClick(githubRepository: GithubRepositoryUIModel)
    }

    companion object {
        private val GITHUB_REPOSITORY_COMPARATOR = object : DiffUtil.ItemCallback<GithubRepositoryUIModel>() {
            override fun areItemsTheSame(
                oldItem: GithubRepositoryUIModel,
                newItem: GithubRepositoryUIModel
            ) = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: GithubRepositoryUIModel,
                newItem: GithubRepositoryUIModel
            ) = oldItem == newItem

        }
    }

}