package com.zntkr.deneme_fly.adapter

import android.app.Activity
import android.app.Application
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.lifecycle.*
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.zntkr.deneme_fly.R
import com.zntkr.deneme_fly.Repository
import com.zntkr.deneme_fly.databinding.MyFlightsRecyclerRowBinding
import com.zntkr.deneme_fly.model.RoomModel
import com.zntkr.deneme_fly.roomdb.InfoDao
import com.zntkr.deneme_fly.roomdb.InfoDatabase
import com.zntkr.deneme_fly.viewmodel.MyFlightsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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


    }

    override fun getItemCount(): Int {
        return list.size
    }



}

