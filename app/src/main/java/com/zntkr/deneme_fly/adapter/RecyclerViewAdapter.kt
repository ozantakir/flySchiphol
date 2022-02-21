package com.zntkr.deneme_fly.adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zntkr.deneme_fly.R
import com.zntkr.deneme_fly.databinding.RecyclerRowBinding
import com.zntkr.deneme_fly.model.Flight
class RecyclerViewAdapter(private val flyList : List<Flight>,val listener: MyOnClickListener) :
    RecyclerView.Adapter<RecyclerViewAdapter.RowHolder>() {

    inner class RowHolder(val binding: RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root){
        // on click listener
        init {
            itemView.setOnClickListener {
                val pos = absoluteAdapterPosition
                listener.onClick(pos)
            }
        }
    }
    interface MyOnClickListener{
        fun onClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context))
        return RowHolder(binding)
    }

    override fun onBindViewHolder(holder: RowHolder, position: Int) {
        // assigning values to ui
        val airport = flyList[position].route?.destinations?.get(0) + " Airport"
        holder.binding.flightDestination.text = airport
        holder.binding.flightName.text = flyList[position].flightName
        holder.binding.flightDate.text = flyList[position].scheduleDate
        holder.binding.flightTime.text = flyList[position].scheduleTime
        // checking the direction for icon
        if(flyList[position].flightDirection == "A"){
            holder.binding.flyIcon.setImageResource(R.drawable.arrival)
        } else if (flyList[position].flightDirection == "D"){
            holder.binding.flyIcon.setImageResource(R.drawable.departure)
        }
    }

    override fun getItemCount(): Int {
        return flyList.count()
    }
}
























