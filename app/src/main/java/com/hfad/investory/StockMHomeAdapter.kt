package com.hfad.investory

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hfad.investory.API.StockCoins

import com.hfad.investory.databinding.HomeAssetsBlankBinding

class StockMHomeAdapter: ListAdapter<StockCoins, StockMHomeAdapter.StockMHomeHolder>(DiffCallBack()) {

    inner class StockMHomeHolder(val binding: HomeAssetsBlankBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockMHomeHolder {
        val binding = HomeAssetsBlankBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return StockMHomeHolder(binding)
    }

    override fun onBindViewHolder(holder: StockMHomeHolder, position: Int) {
        val active = getItem(position)

        val rawChange = active.changesPercentage.replace(",", ".")
        val changeValue = rawChange.toDouble()

        val roundPriceChange = "%.2f".format(changeValue)
        val roundPrice = "%.2f".format(active.price)

        // Price change colors + text

        if (changeValue >= 0) {
            holder.binding.apply {
                change.setTextColor(ContextCompat.getColor(root.context, R.color.up))
                change.setBackgroundResource(R.drawable.up_bg)
                change.text = "+$roundPriceChange%"
            }
        } else if (changeValue < 0) {
            holder.binding.apply {
                change.setTextColor(ContextCompat.getColor(root.context, R.color.down))
                change.setBackgroundResource(R.drawable.down_bg)
                change.text = "$roundPriceChange%"
            }
        }

        // Data setting
        holder.binding.apply {
            title.text = active.symbol.uppercase()
            price.text = "$roundPrice $"
        }

        // Image setting
        Glide.with(holder.binding.root.context)
            .load(R.drawable.not_available)
            .into(holder.binding.icon)
    }


    private class DiffCallBack : DiffUtil.ItemCallback<StockCoins>() {
        override fun areItemsTheSame(oldItem: StockCoins, newItem: StockCoins): Boolean {
            return oldItem.symbol == newItem.symbol
        }

        override fun areContentsTheSame(oldItem:StockCoins, newItem: StockCoins): Boolean {
            return oldItem == newItem
        }

    }
}
