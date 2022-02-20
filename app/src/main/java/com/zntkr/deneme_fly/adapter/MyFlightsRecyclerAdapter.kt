package com.zntkr.deneme_fly.adapter

import android.app.Activity
import android.content.Intent
import android.view.*
import androidx.recyclerview.widget.RecyclerView
import com.zntkr.deneme_fly.R
import com.zntkr.deneme_fly.databinding.MyFlightsRecyclerRowBinding
import com.zntkr.deneme_fly.model.RoomModel
import com.zntkr.deneme_fly.view.MyFlightsDetailsActivity

class MyFlightsRecyclerAdapter(private val list : List<RoomModel>) :
    RecyclerView.Adapter<MyFlightsRecyclerAdapter.ItemHolder>(){


    class ItemHolder(val binding: MyFlightsRecyclerRowBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val binding = MyFlightsRecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ItemHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {

        if(list[position].direction == "A"){
            holder.binding.flyIcon.setImageResource(R.drawable.arrival)
        } else if (list[position].direction == "D"){
            holder.binding.flyIcon.setImageResource(R.drawable.departure)
        }

        holder.binding.flightDate.text = list[position].date
        holder.binding.flightDestination.text = list[position].destination
        holder.binding.flightTime.text = list[position].time
        holder.binding.flightName.text = list[position].flightName
        holder.binding.name.text = list[position].name

        holder.itemView.setOnClickListener {
            val activity = holder.itemView.context as Activity
            val intent = Intent(activity, MyFlightsDetailsActivity::class.java)
            val list = arrayListOf<String>(list[position].name,list[position].flightName,list[position].destination,
                list[position].time,list[position].date)
            intent.putStringArrayListExtra("list",list)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}

