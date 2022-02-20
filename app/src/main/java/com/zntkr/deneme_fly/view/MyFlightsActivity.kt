package com.zntkr.deneme_fly.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.media.Image
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.zntkr.deneme_fly.R
import com.zntkr.deneme_fly.adapter.MyFlightsRecyclerAdapter
import com.zntkr.deneme_fly.adapter.RecyclerViewAdapter
import com.zntkr.deneme_fly.databinding.ActivityMyFlightsBinding
import com.zntkr.deneme_fly.databinding.MyFlightsRecyclerRowBinding
import com.zntkr.deneme_fly.model.RoomModel
import com.zntkr.deneme_fly.roomdb.InfoDatabase
import com.zntkr.deneme_fly.viewmodel.MyFlightsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class MyFlightsActivity : AppCompatActivity(), LifecycleOwner {

    private lateinit var binding: ActivityMyFlightsBinding
    private var recyclerAdapter: MyFlightsRecyclerAdapter? = null
    lateinit var viewModel: MyFlightsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyFlightsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        // initializing view model
        viewModel = ViewModelProvider(this)[MyFlightsViewModel::class.java]

        // loading all of the from room
        getAll()

        // getting past flights
        binding.pastFlights.setOnCheckedChangeListener { button, isChecked ->
            if (isChecked) {
                getPast()
            } else {
                getAll()
            }
        }

        // getting future flights
        binding.futureFlights.setOnCheckedChangeListener { button, isChecked ->
            if (isChecked) {
                getFuture()
            } else {
                getAll()
            }
        }

        // moving to main screen
        binding.homeButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        // moving to scan activity
        binding.scan.setOnClickListener {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 1)
            if(ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
                val intent = Intent(this, ScanActivity::class.java)
                startActivity(intent)
            }
        }

        binding.delete.setOnClickListener {
                viewModel.deleteAll()
        }
    }

    private fun getAll() {
        viewModel.getAll.observe(this, Observer {
            recyclerAdapter = MyFlightsRecyclerAdapter(it)
            binding.recyclerView.adapter = recyclerAdapter
        })
    }

    private fun getPast() {
        viewModel.getPast.observe(this, Observer {
            recyclerAdapter = MyFlightsRecyclerAdapter(it)
            binding.recyclerView.adapter = recyclerAdapter
        })
    }

    private fun getFuture() {
        viewModel.getFuture.observe(this, Observer {
            recyclerAdapter = MyFlightsRecyclerAdapter(it)
            binding.recyclerView.adapter = recyclerAdapter
        })
    }
    // preventing back button functionality
    @Override
    override fun onBackPressed() {
        return;
    }

    fun getNow() : Long {
        val year = Calendar.getInstance().get(Calendar.YEAR)
        var month = Calendar.getInstance().get(Calendar.MONTH)
        if (month < 10){
            month = "0$month".toInt()
        }
        var day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        if (day < 10){
            day = "0$day".toInt()
        }
        var hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        if (hour < 10){
            hour = "0$hour".toInt()
        }
        var minute = Calendar.getInstance().get(Calendar.MINUTE)
        if (minute < 10){
            minute = "0$minute".toInt()
        }
        var second = Calendar.getInstance().get(Calendar.SECOND)
        if (second < 10){
            second = "0$second".toInt()
        }

        val dateTime = "$year$month$day$hour$minute$second"
        return dateTime.toLong()
    }
}