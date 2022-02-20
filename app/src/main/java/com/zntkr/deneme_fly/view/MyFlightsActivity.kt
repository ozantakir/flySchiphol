package com.zntkr.deneme_fly.view

import android.content.Intent
import android.media.Image
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
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

class MyFlightsActivity : AppCompatActivity(), LifecycleOwner {

    private lateinit var binding: ActivityMyFlightsBinding
    private var recyclerAdapter: MyFlightsRecyclerAdapter? = null
    lateinit var viewModel: MyFlightsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyFlightsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel = ViewModelProvider(this)[MyFlightsViewModel::class.java]
        getAll()

        binding.pastFlights.setOnCheckedChangeListener { button, isChecked ->
            if (isChecked) {
                getPast()
            } else {
                getAll()
            }
        }
        binding.futureFlights.setOnCheckedChangeListener { button, isChecked ->
            if (isChecked) {
                getFuture()
            } else {
                getAll()
            }
        }

        binding.homeButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.scan.setOnClickListener {
            val intent = Intent(this, ScanActivity::class.java)
            startActivity(intent)
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

    @Override
    override fun onBackPressed() {
        return;
    }
}