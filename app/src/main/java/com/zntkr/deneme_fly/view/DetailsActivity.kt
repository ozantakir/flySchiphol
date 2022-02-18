package com.zntkr.deneme_fly.view


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.zntkr.deneme_fly.R
import com.zntkr.deneme_fly.databinding.ActivityDetailsBinding
import com.zntkr.deneme_fly.model.Destinations
import com.zntkr.deneme_fly.service.DestinationApi
import kotlinx.coroutines.*
import retrofit2.Response

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDetailsBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = getIntent()
        val name = intent.getStringExtra("flyName")
        binding.flightName.text = name
        val reservation = intent.getStringExtra("reservation")
        if(reservation == "true"){
            binding.reservation.visibility = View.VISIBLE
            binding.seat.visibility = View.VISIBLE
            binding.seats.visibility = View.VISIBLE
            binding.seatsSpinner.visibility = View.VISIBLE
        } else {
            binding.reservation.visibility = View.GONE
            binding.seat.visibility = View.GONE
            binding.seats.visibility = View.GONE
            binding.seatsSpinner.visibility = View.GONE
        }
        val direction = intent.getStringExtra("flyDirection")
        if(direction == "A"){
            binding.gate.visibility = View.GONE
            binding.flightGate.visibility = View.GONE
            binding.imageDetails.setImageResource(R.drawable.arrival)
        } else if (direction == "D"){
            binding.gate.visibility = View.VISIBLE
            binding.flightGate.visibility = View.VISIBLE
            binding.imageDetails.setImageResource(R.drawable.departure)
        }
        val date = intent.getStringExtra("flyDate")
        val time = intent.getStringExtra("flyTime")
        val number = intent.getStringExtra("flyNumber")
        val gate = intent.getStringExtra("flyGate")
        val destination = intent.getStringExtra("flyDestination")


        binding.date.text = date
        binding.time.text = time
        if(gate == "null"){
            binding.gate.setText(R.string.not_decided)
        } else {
            binding.gate.text = gate
        }
        binding.number.text = number
        binding.destination.text = destination

        binding.reservation.setOnClickListener {
            val newIntent = Intent(this,ReservationActivity::class.java)
            newIntent.putExtra("flightName",name)
            newIntent.putExtra("flightDate",date)
            newIntent.putExtra("flightTime",time)
            newIntent.putExtra("flightSeat",binding.seatsSpinner.selectedItem.toString())
            startActivity(newIntent)
        }


    }


}