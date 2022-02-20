package com.zntkr.deneme_fly.adapter
import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zntkr.deneme_fly.R
import com.zntkr.deneme_fly.databinding.RecyclerRowBinding
import com.zntkr.deneme_fly.model.Flight
import com.zntkr.deneme_fly.model.FlyModel
import com.zntkr.deneme_fly.service.DestinationApi
import com.zntkr.deneme_fly.view.DetailsActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.collections.ArrayList

class RecyclerViewAdapter(private val flyList : List<Flight>) :
    RecyclerView.Adapter<RecyclerViewAdapter.RowHolder>() {
    private var city : String? = null
    private var country : String? = null
    private var dest : String? = null

    class RowHolder(val binding: RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context))
        return RowHolder(binding)
    }

    override fun onBindViewHolder(holder: RowHolder, position: Int) {

        // getting destination country and city name from api and assigning
        val quotesApi = RetrofitHelper.getInstance().create(DestinationApi::class.java)
        CoroutineScope(Dispatchers.IO).launch {
            val destination = flyList[position].route?.destinations?.get(0)
            val result = quotesApi.getDestinations(destination!!)
            withContext(Dispatchers.Main){
                city = result.body()?.city.toString()
                country = result.body()?.country
                dest = "$city, $country"
                holder.binding.flightDestination.text = dest
            }
        }
        // assigning values to ui
        holder.binding.flightName.text = flyList[position].flightName
        holder.binding.flightDate.text = flyList[position].scheduleDate
        holder.binding.flightTime.text = flyList[position].scheduleTime
        if(flyList[position].flightDirection == "A"){
            holder.binding.flyIcon.setImageResource(R.drawable.arrival)
        } else if (flyList[position].flightDirection == "D"){
            holder.binding.flyIcon.setImageResource(R.drawable.departure)
        }

        // getting time for now
        val year = Calendar.getInstance().get(Calendar.YEAR)
        val month = Calendar.getInstance().get(Calendar.MONTH)
        val day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)

        // time for selected flight
        val flyDate = flyList[position].scheduleDate
        val flyYear = flyDate?.substring(0,4)?.toInt()
        val flyMonth = flyDate?.substring(5,7)?.toInt()
        val flyDay = flyDate?.substring(8,10)?.toInt()


            holder.itemView.setOnClickListener {
                val activity = holder.itemView.context as Activity
                val intent = Intent(activity,DetailsActivity::class.java)

                // comparing flight direction and time to decide if reservation is possible
                if(flyList[position].flightDirection == "D" && year <= flyYear!!){
                    if(month <= flyMonth!!){
                        if(day < flyDay!!){
                            intent.putExtra("reservation","true")
                        }
                    }
                } else {
                    intent.putExtra("reservation","false")
                }

                // sending data with intent and starting activity
                intent.putExtra("flyName", flyList[position].flightName)
                intent.putExtra("flyNumber", flyList[position].flightNumber.toString())
                intent.putExtra("flyDirection", flyList[position].flightDirection)
                intent.putExtra("flyGate", flyList[position].gate.toString())
                intent.putExtra("flyDate", flyList[position].scheduleDate)
                intent.putExtra("flyTime", flyList[position].scheduleTime)
                intent.putExtra("flyDestination",
                    holder.binding.flightDestination.text
                )
                holder.itemView.context.startActivity(intent)
            }
    }

    override fun getItemCount(): Int {
        return flyList.count()
    }
}