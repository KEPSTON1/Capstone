package com.example.capstone.ui.article

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.capstone.api.response.Article
import com.example.capstone.databinding.ItemArticleBinding

class ArticleAdapter(private val articles: List<Article>) :
    RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class ViewHolder(val binding: ItemArticleBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = articles[position]

        Glide.with(holder.itemView.context)
            .load(article.urlToImage)
            .into(holder.binding.ivArticleImage)

        holder.binding.tvArticleTitle.text = article.title
        holder.binding.tvArticleDescription.text = article.description

        holder.itemView.setOnClickListener {
            onItemClickCallback?.onItemClicked(article)
        }
    }

    override fun getItemCount(): Int = articles.size

    interface OnItemClickCallback {
        fun onItemClicked(data: Article)
    }
}