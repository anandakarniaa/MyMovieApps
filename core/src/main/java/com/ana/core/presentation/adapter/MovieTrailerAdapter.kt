package com.ana.core.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.webkit.WebChromeClient
import androidx.recyclerview.widget.RecyclerView
import com.ana.core.databinding.ItemTrailerMovieBinding
import com.ana.core.domain.model.MovieTrailer
import com.ana.core.utils.commonYoutubeUrl

class MovieTrailerAdapter(private val itemClick: (MovieTrailer) -> Unit) :
    RecyclerView.Adapter<MovieTrailerAdapter.MovieTrailerViewHolder>() {


    private var items: MutableList<MovieTrailer> = mutableListOf()

    fun setItems(items: List<MovieTrailer>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieTrailerViewHolder {
        val binding = ItemTrailerMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieTrailerViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(holder: MovieTrailerViewHolder, position: Int) {
        holder.bindView(items[position])
    }

    override fun getItemCount(): Int = items.size


    class MovieTrailerViewHolder(
        private val binding: ItemTrailerMovieBinding,
        val itemClick: (MovieTrailer) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindView(item: MovieTrailer) {
            with(item) {
                itemView.setOnClickListener { itemClick(this) }
                binding.wvTrailer.apply {
                    settings.javaScriptEnabled = true
                    settings.loadWithOverviewMode = true
                    settings.useWideViewPort = true
                    webChromeClient = WebChromeClient()
                    loadUrl(key.commonYoutubeUrl())
                }
            }
        }
    }

}