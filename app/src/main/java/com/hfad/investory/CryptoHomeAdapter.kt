package com.hfad.investory

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hfad.investory.API.CryptoCoin
import com.hfad.investory.databinding.HomeAssetsBlankBinding

class CryptoHomeAdapter() : ListAdapter<CryptoCoin, CryptoHomeAdapter.CryptoHomeHolder>(DiffCallBack()) {

    inner class CryptoHomeHolder(val binding: HomeAssetsBlankBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoHomeHolder {
        val binding = HomeAssetsBlankBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return CryptoHomeHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CryptoHomeHolder, position: Int) {
        val coin = getItem(position)

        val roundPriceChange = "%.2f".format(coin.priceChange)
        val roundPrice = "%.2f".format(coin.currentPrice)


        // Price change colors + text
        if (coin.priceChange >= 0) {
            holder.binding.apply {
                change.setTextColor(ContextCompat.getColor(root.context, R.color.up))
                change.setBackgroundResource(R.drawable.up_bg)
                change.text = "+$roundPriceChange%"
            }
        } else if (coin.priceChange < 0) {
            holder.binding.apply {
                change.setTextColor(ContextCompat.getColor(root.context, R.color.down))
                change.setBackgroundResource(R.drawable.down_bg)
                change.text = "$roundPriceChange%"
            }
        }

        // Data setting
        holder.binding.apply {
            title.text = coin.symbol.uppercase()
            price.text = "$roundPrice $"
        }

        // Image setting (loaded, notAvailable, notLoaded)
        Glide.with(holder.binding.root.context)
            .load(coin.image)
            .placeholder(R.drawable.not_available)
            .error(R.drawable.loading_error)
            .into(holder.binding.icon)
    }


    private class DiffCallBack : DiffUtil.ItemCallback<CryptoCoin>() {
        override fun areItemsTheSame(oldItem: CryptoCoin, newItem: CryptoCoin): Boolean {
            return oldItem.symbol == newItem.symbol
        }

        override fun areContentsTheSame(oldItem: CryptoCoin, newItem: CryptoCoin): Boolean {
            return oldItem == newItem
        }

    }
}
