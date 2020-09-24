package com.hugo.test.ui.outcar.adapter

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.hugo.test.databinding.ItemCarBinding
import com.hugo.test.model.CarModel
import com.hugo.test.utils.DateUtils
import java.util.*

class CarHolder (private val binding: ItemCarBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: CarModel, listener: (CarModel) -> Unit, context: Context) = with(binding) {

        binding.txPlaca.text="Placa: ${item.placa}"
        binding.txModel.text="Modelo: ${item.model}"
        binding.txDate.text="Entrada: ${DateUtils.getFormatDate(Date(item.time))}"
        binding.txObs.text="Observaciones: ${item.observations}"
        if(item.isOffice==1){
            binding.txOffice.visibility= View.VISIBLE
        }else
        {
            binding.txOffice.visibility= View.GONE
        }

        root.setOnClickListener {
            listener(item)
        }
    }


}