package com.hugo.test.ui.outcar.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hugo.test.databinding.ItemCarBinding
import com.hugo.test.model.CarModel

class AdapterCars (private val listener: (CarModel) -> Unit) : RecyclerView.Adapter<CarHolder>() {

    lateinit var context: Context
    var list: List<CarModel> = mutableListOf()
    fun setData(
        context: Context,
        list: List<CarModel>
    ) {
        this.context = context
        this.list = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarHolder {
        return CarHolder(ItemCarBinding.inflate(LayoutInflater.from(parent.context)))

    }

    override fun onBindViewHolder(holder: CarHolder, position: Int) {
        val element = list[position]
        holder.bind(element, listener, context)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int {
        return list.size
    }
}