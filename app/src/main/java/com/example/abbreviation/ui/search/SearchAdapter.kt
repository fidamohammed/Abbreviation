package com.example.abbreviation.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.abbreviation.R
import com.example.abbreviation.data.model.LfModel
import com.example.abbreviation.data.model.LongFormItemModel
import com.example.abbreviation.databinding.SearchResultItemBinding


class SearchAdapter(val sf: String, val lfs:List<LfModel>): RecyclerView.Adapter<SearchAdapter.ItemViewHolder>() {
    class ItemViewHolder(meaningItem: View): RecyclerView.ViewHolder(meaningItem){
        val binding = SearchResultItemBinding.bind(meaningItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.search_result_item,parent,false)
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        //var lfs = meaningList[position].lfs
        holder.binding.apply {
            tvLongForm.text = lfs[position].lf
            tvFrequency.text = lfs[position].freq.toString()
            tvSince.text = lfs[position].since.toString()
        }
        //holder.binding.tvLongForm.text = lfs[position].lf
        //holder.binding.tvFrequency.text = lfs[position].freq.toString()
        //holder.binding.
    }

    override fun getItemCount(): Int {
        return lfs.size

    }




}