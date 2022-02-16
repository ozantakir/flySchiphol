package com.zntkr.deneme_fly.adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zntkr.deneme_fly.databinding.RecyclerRowBinding
import com.zntkr.deneme_fly.model.Flight
import com.zntkr.deneme_fly.model.FlyModel

class RecyclerViewAdapter(private val flyList : FlyModel) :
    RecyclerView.Adapter<RecyclerViewAdapter.RowHolder>() {
    class RowHolder(val binding: RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return RowHolder(binding)
    }

    override fun onBindViewHolder(holder: RowHolder, position: Int) {
        holder.binding.flightName.text = flyList.flights?.get(position)?.flightName
        holder.binding.flightNumber.text = flyList.flights?.get(position)?.flightNumber.toString()
    }

    override fun getItemCount(): Int {
        return flyList.flights!!.count()
    }
}