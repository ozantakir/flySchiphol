package com.zntkr.deneme_fly.view
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.zntkr.deneme_fly.R
import com.zntkr.deneme_fly.databinding.ActivityDetailsBinding
import com.zntkr.deneme_fly.model.Flight

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Intent to get data from main activity
        val intent = getIntent()

        // Getting data from intent
        val name = intent.getStringExtra("flyName")
        val date = intent.getStringExtra("flyDate")
        val time = intent.getStringExtra("flyTime")
        val number = intent.getStringExtra("flyNumber")
        val gate = intent.getStringExtra("flyGate")
        val destination = intent.getStringExtra("flyDestination")


        // Checking if reservation is possible or not
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

        // Checking the direction of flight
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

        // Assigning values to ui
        binding.flightName.text = name
        binding.date.text = date
        binding.time.text = time
        if(gate == "null"){
            binding.gate.setText(R.string.not_decided)
        } else {
            binding.gate.text = gate
        }
        binding.number.text = number
        binding.destination.text = destination

        // Intent to reservation activity
        binding.reservation.setOnClickListener {
            val newIntent = Intent(this,ReservationActivity::class.java)
            newIntent.putExtra("flightName",name)
            newIntent.putExtra("flightDate",date)
            newIntent.putExtra("flightTime",time)
            newIntent.putExtra("flightDirection",direction)
            newIntent.putExtra("flightDestination",destination)
            newIntent.putExtra("flightSeat",binding.seatsSpinner.selectedItem.toString())
            startActivity(newIntent)
        }
    }
}