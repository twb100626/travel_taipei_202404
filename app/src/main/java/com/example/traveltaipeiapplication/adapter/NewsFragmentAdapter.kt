package com.example.traveltaipeiapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.traveltaipeiapplication.model.News
import com.example.traveltaipeiapplication.databinding.LayoutListNewsBinding
import com.example.traveltaipeiapplication.model.NewsItem

class NewsFragmentAdapter(val listener : View.OnClickListener?, val bottomReachedListener: OnBottomReachedListener?) : RecyclerView.Adapter<NewsFragmentAdapter.ViewHolder>() {

    private val newsList = mutableListOf<News>()

    class ViewHolder : RecyclerView.ViewHolder {

        var mBinding : LayoutListNewsBinding? = null
        constructor(binding : LayoutListNewsBinding) : super(binding.root) {
            mBinding = binding
        }

        fun bind(news : News) {
            var item = NewsItem(news)
            mBinding?.item = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding  = LayoutListNewsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false)
        if (listener != null) binding.root.setOnClickListener(listener)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    override fun onBindViewHolder(holder: NewsFragmentAdapter.ViewHolder, position: Int) {
        var news = newsList.get(position)
        holder.itemView.setTag(news);
        holder.bind(news)
        bottomReachedListener?.let {
            if (position == newsList.size - 1)
                it.onBottomReached(position)
        }
    }

    fun addItems(list : List<News>) {
        newsList.addAll(list)
        notifyDataSetChanged()
    }
}