package com.zntkr.deneme_fly.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.zntkr.deneme_fly.R
import com.zntkr.deneme_fly.databinding.ActivityReservationBinding
import com.zntkr.deneme_fly.model.RoomModel
import com.zntkr.deneme_fly.roomdb.InfoDao
import com.zntkr.deneme_fly.roomdb.InfoDatabase
import com.zntkr.deneme_fly.viewmodel.ReservationViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ReservationActivity : AppCompatActivity() {

    private lateinit var viewModel : ReservationViewModel

    private lateinit var binding : ActivityReservationBinding
    private lateinit var db : InfoDatabase
    private lateinit var infoDao : InfoDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReservationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // initialize view model
        viewModel = ViewModelProvider(this).get(ReservationViewModel::class.java)
//
//        // database
//        db = Room.databaseBuilder(applicationContext,InfoDatabase::class.java,"Information").build()
//        infoDao = db.infoDao()

        // Intent to get data from details activity
        val intent = getIntent()

        // Getting data from intent
        val flightName = intent.getStringExtra("flightName")
        val flightDate = intent.getStringExtra("flightDate")
        val flightTime = intent.getStringExtra("flightTime")
        val flightSeat = intent.getStringExtra("flightSeat")
        val flightDirection = intent.getStringExtra("flightDirection")
        val flightDestination = intent.getStringExtra("flightDestination")

            binding.confirmButton.setOnClickListener {
                val date = flightDate?.replace("-","")
                val time = flightTime?.replace(":","")
                val dateTime = (date + time).toLong()

                // getting input data
                val name =  binding.editName.text.toString()
                val phone = binding.editPhone.text.toString()
                val email = binding.editMail.text.toString()
                if(name.isNotEmpty() && phone.isNotEmpty() && email.isNotEmpty()){
                    val roomModel = RoomModel(name = name, email = email, phone = phone,flightName = flightName!!,
                        date = flightDate!!, time = flightTime!!, seat = flightSeat!!, destination = flightDestination!!,
                        direction = flightDirection!!, dateTime = dateTime)
                    viewModel.addData(roomModel)

//                CoroutineScope(Dispatchers.IO).launch {
//                    val info = RoomModel(name = name, email = email, phone = phone,flightName = flightName!!,
//                        date = flightDate!!, time = flightTime!!, seat = flightSeat!!, destination = flightDestination!!,
//                        direction = flightDirection!!, dateTime = dateTime)
//                    infoDao.insert(info)
//                }
                val newIntent = Intent(this, MyFlightsActivity::class.java)
                startActivity(newIntent)
            } else {
                    Toast.makeText(this,"Fill in the blanks",Toast.LENGTH_SHORT).show()
                }
            }
    }
}