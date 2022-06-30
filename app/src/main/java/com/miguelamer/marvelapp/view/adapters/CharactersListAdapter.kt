package com.miguelamer.marvelapp.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.miguelamer.marvelapp.R
import com.miguelamer.marvelapp.databinding.CharacterRecyclerviewItemBinding
import com.miguelamer.marvelapp.databinding.LoadingRecyclerviewItemBinding
import com.miguelamer.marvelapp.domain.model.MarvelCharacter
import com.miguelamer.marvelapp.view.interfaces.RecyclerViewClickListener

class CharactersListAdapter(var dataset: MutableList<MarvelCharacter?>, val listener: RecyclerViewClickListener): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_LOADING = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return if (viewType == VIEW_TYPE_ITEM) {
            val binding = CharacterRecyclerviewItemBinding.inflate(layoutInflater, parent, false)
            CharacterViewHolder(binding)
        } else {
            val binding = LoadingRecyclerviewItemBinding.inflate(layoutInflater, parent, false)
            LoadingViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is CharacterViewHolder) {
            dataset[position]?.let { holder.bind(it, listener, position) }
        }
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    class CharacterViewHolder(private val binding: CharacterRecyclerviewItemBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(
            marvelCharacter: MarvelCharacter,
            listener: RecyclerViewClickListener,
            position: Int
        ) {
            if (marvelCharacter.description == "") {
                binding.characterDescriptionTextview.text = binding.root.context.getString(R.string.no_description_available)
            } else {
                binding.characterDescriptionTextview.text = marvelCharacter.description
            }
            binding.characterNameTextview.text = marvelCharacter.name
            Glide.with(binding.root).load(marvelCharacter.thumbnail.getUrl()).into(binding.thumbnailImageview)
            binding.root.setOnClickListener {
                listener.onItemClicked(position)
            }
        }
    }

    class LoadingViewHolder(private val binding: LoadingRecyclerviewItemBinding): RecyclerView.ViewHolder(binding.root) {


    }

    override fun getItemViewType(position: Int): Int {
        return if (dataset[position] == null) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
    }
}