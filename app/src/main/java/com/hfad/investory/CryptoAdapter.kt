package com.hfad.investory

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hfad.investory.database.MyCrypto
import com.hfad.investory.databinding.HomeAssetsBlankBinding

class CryptoAdapter(
    private val onItemLongClick: (MyCrypto) -> Unit
) : ListAdapter<MyCrypto, CryptoAdapter.CryptoViewHolder>(DiffCallback()) {

    inner class CryptoViewHolder(val binding: HomeAssetsBlankBinding)
        : RecyclerView.ViewHolder(binding.root) {
            fun bind(item: MyCrypto) {
                binding.title.text = item.symbol
                binding.root.setOnLongClickListener {
                    onItemLongClick(item)
                    true
                }
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoViewHolder {
        val binding = HomeAssetsBlankBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return CryptoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CryptoViewHolder, position: Int) {
        holder.bind(getItem(position))

        val item = getItem(position)

        val readyPrice = "%.2f".format(item.totalValue).toString()

        holder.binding.apply {
            title.text = item.symbol
            change.text = item.amount.toString()
            change.setTextColor(ContextCompat.getColor(root.context, R.color.legend_text))
            change.setBackgroundResource(R.drawable.text_bg)
            price.text = readyPrice

            // Image setting
            Glide.with(holder.binding.root.context)
                .load(item.iconUrl)
                .into(holder.binding.icon)
        }
    }


    class DiffCallback : DiffUtil.ItemCallback<MyCrypto>() {
        override fun areItemsTheSame(oldItem: MyCrypto, newItem: MyCrypto): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MyCrypto, newItem: MyCrypto): Boolean {
            return oldItem == newItem
        }
    }
}