package com.hugo.test.ui.settings.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hugo.test.databinding.ItemCarBinding
import com.hugo.test.databinding.ItemDetailPayBinding
import com.hugo.test.model.CarModel
import com.hugo.test.model.ResidentModel

class DetailPayAdapter (private val listener: (ResidentModel) -> Unit) : RecyclerView.Adapter<DetailHolder>() {

    lateinit var context: Context
    var list: List<ResidentModel> = mutableListOf()
    fun setData(
        context: Context,
        list: List<ResidentModel>
    ) {
        this.context = context
        this.list = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailHolder {
        return DetailHolder(ItemDetailPayBinding.inflate(LayoutInflater.from(parent.context)))

    }

    override fun onBindViewHolder(holder: DetailHolder, position: Int) {
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