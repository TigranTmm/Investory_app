package com.hfad.investory

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hfad.investory.database.MyStock
import com.hfad.investory.databinding.HomeAssetsBlankBinding
import java.text.NumberFormat
import java.util.Locale

class StockAdapter(
    private val onItemLongClick: (MyStock) -> Unit
) : ListAdapter<MyStock, StockAdapter.StockViewHolder>(DiffCallback()) {

    inner class StockViewHolder(val binding: HomeAssetsBlankBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MyStock) {
            binding.title.text = item.symbol
            binding.root.setOnLongClickListener {
                onItemLongClick(item)
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockViewHolder {
        val binding = HomeAssetsBlankBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return StockViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StockViewHolder, position: Int) {
        holder.bind(getItem(position))

        val item = getItem(position)

        val formatter = NumberFormat.getCurrencyInstance(Locale.US)
        val readyPrice = formatter.format(item.totalValue)

        holder.binding.apply {
            title.text = item.symbol
            change.text = item.amount.toString()
            change.setTextColor(ContextCompat.getColor(root.context, R.color.legend_text))
            change.setBackgroundResource(R.drawable.text_bg)
            price.text = readyPrice

            // Image setting
            Glide.with(holder.binding.root.context)
                .load(R.drawable.active)
                .into(holder.binding.icon)
        }
    }


    class DiffCallback : DiffUtil.ItemCallback<MyStock>() {
        override fun areItemsTheSame(oldItem: MyStock, newItem: MyStock): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MyStock, newItem: MyStock): Boolean {
            return oldItem == newItem
        }
    }
}