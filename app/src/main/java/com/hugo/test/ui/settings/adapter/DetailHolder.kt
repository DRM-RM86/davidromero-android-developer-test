package com.hugo.test.ui.settings.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.hugo.test.databinding.ItemDetailPayBinding
import com.hugo.test.model.ResidentModel

class DetailHolder (private val binding: ItemDetailPayBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: ResidentModel, listener: (ResidentModel) -> Unit, context: Context) = with(binding) {
        binding.txPlaca.text=item.placa
        binding.txMinutes.text="30"
        binding.txPay.text="$100.00"

        root.setOnClickListener {
            listener(item)
        }
    }


}