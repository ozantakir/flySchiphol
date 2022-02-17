package com.zntkr.deneme_fly.adapter
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.zntkr.deneme_fly.R
import com.zntkr.deneme_fly.databinding.RecyclerRowBinding
import com.zntkr.deneme_fly.model.Flight
import com.zntkr.deneme_fly.model.FlyModel
import kotlin.coroutines.coroutineContext

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
        holder.binding.flightDate.text = flyList.flights?.get(position)?.scheduleDate
        holder.binding.flightTime.text = flyList.flights?.get(position)?.scheduleTime
        if(flyList.flights?.get(position)?.flightDirection == "A"){
            holder.binding.flyIcon.setImageResource(R.drawable.arrival)
        } else if (flyList.flights?.get(position)?.flightDirection == "D"){
            holder.binding.flyIcon.setImageResource(R.drawable.departure)
        }
    }

    override fun getItemCount(): Int {
        return flyList.flights!!.count()
    }
}