package com.example.capstone.ui.article

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.capstone.R
import com.example.capstone.api.response.Article

class ArticleAdapter(private val articles: List<Article>) :
    RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivArticleImage: ImageView = itemView.findViewById(R.id.iv_article_image)
        val tvArticleTitle: TextView = itemView.findViewById(R.id.tv_article_title)
        val tvArticleDescription: TextView = itemView.findViewById(R.id.tv_article_description)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_article, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = articles[position]

        Glide.with(holder.itemView.context)
            .load(article.urlToImage)
            .into(holder.ivArticleImage)

        holder.tvArticleTitle.text = article.title
        holder.tvArticleDescription.text = article.description
    }

    override fun getItemCount(): Int = articles.size
}