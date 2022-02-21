package com.zntkr.deneme_fly.view
import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AbsListView
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zntkr.deneme_fly.R
import com.zntkr.deneme_fly.adapter.RecyclerViewAdapter
import com.zntkr.deneme_fly.model.FlyModel
import com.zntkr.deneme_fly.service.QApi
import com.zntkr.deneme_fly.databinding.ActivityMainBinding
import com.zntkr.deneme_fly.model.Destination
import com.zntkr.deneme_fly.model.DestinationsList
import com.zntkr.deneme_fly.model.Flight
import com.zntkr.deneme_fly.service.DestinationApi
import com.zntkr.deneme_fly.viewmodel.MainViewModel
import com.zntkr.deneme_fly.viewmodel.ReservationViewModel
import kotlinx.coroutines.*
import retrofit2.Response
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel : MainViewModel
    private var flyModels: List<Flight>? = null
    private var recyclerViewAdapter : RecyclerViewAdapter? = null
    private lateinit var binding : ActivityMainBinding
    private lateinit var dateText : String
    private var date : String? = null

    private var selectedYear = 0
    private var selectedMonth = 0
    private var selectedDay = 0
    private var destination = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        val layoutManager : RecyclerView.LayoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager

        // loading all of the data
        loadData()
        // Next - Previous page
        binding.fab.setOnClickListener {
            viewModel.addNumber()
                loadData()
        }
        binding.fab2.setOnClickListener {
            viewModel.subNumber()
                loadData()

        }
        // Filtering according to Arrival
        binding.checkArrival.setOnCheckedChangeListener { button, isChecked ->
            if(isChecked && !binding.checkDeparture.isChecked){
                destination = "A"
                loadData()
            } else if(!isChecked && binding.checkDeparture.isChecked){
                destination = "D"
                loadData()
            } else {
                destination = ""
                loadData()
            }
        }
        // Filtering according to Departure
        binding.checkDeparture.setOnCheckedChangeListener { button, isChecked ->
            if(isChecked && !binding.checkArrival.isChecked){
                //  binding.checkArrival.isChecked = false
                destination = "D"
                loadData()
            } else if(!isChecked && binding.checkArrival.isChecked){
                destination = "A"
                loadData()
            } else {
                destination = ""
                loadData()
            }
        }

        // Choosing date from Calendar
        binding.selectDate.setOnClickListener {
            getDate()
        }
        // Filtering according to calendar
        binding.selectDate.addTextChangedListener {
            if(binding.selectDate.text.toString() != "Select Date"){
                date = binding.selectDate.text.toString()
                loadData()
            }
        }
        // Clearing all filters
        binding.clear.setOnClickListener {
            binding.checkArrival.isChecked = false
            binding.selectDate.setText(R.string.select_date)
            date = null
            binding.checkDeparture.isChecked = false
            loadData()
        }
        // Navigate to My flights
        binding.flightsButton.setOnClickListener {
            val intent = Intent(this, MyFlightsActivity::class.java)
            startActivity(intent)
        }


    }

    // Function to load data from API
    private fun loadData() {
        if(destination == "A"){
            viewModel.refreshPage(dest = "A",date = date)
        } else if(destination == "D"){
            viewModel.refreshPage(dest = "D",date = date)
        } else {
            viewModel.refreshPage(dest = null,date = date)
        }
        viewModel.pageLiveData.observe(this){response ->
            if(response != null){
                flyModels = response.flights
                flyModels?.let {
                    recyclerViewAdapter = RecyclerViewAdapter(it)
                    binding.recyclerView.adapter = recyclerViewAdapter
                }
            }
        }
    }
    private fun getDate(){
        val currentDate = Calendar.getInstance()
        val year = currentDate.get(Calendar.YEAR)
        val month = currentDate.get(Calendar.MONTH)
        val day = currentDate.get(Calendar.DAY_OF_MONTH)

        if(binding.selectDate.text.isNotEmpty()){
            this.selectedYear = year
            this.selectedMonth = month
            this.selectedDay = day
        }
        val listener = DatePickerDialog.OnDateSetListener{datePicker, selectedYear, selectedMonth, selectedDay ->
            this.selectedYear = selectedYear
            this.selectedMonth = selectedMonth
            this.selectedDay = selectedDay
            if(selectedMonth < 10 && selectedDay < 10){
                dateText = "$selectedYear-0${selectedMonth + 1}-0$selectedDay"
            } else if(selectedMonth < 10){
                dateText = "$selectedYear-0${selectedMonth + 1}-$selectedDay"
            } else if(selectedDay < 10){
                dateText = "$selectedYear-${selectedMonth + 1}-0$selectedDay"
            } else {
                dateText = "$selectedYear-${selectedMonth + 1}-$selectedDay"
            }
            binding.selectDate.text = dateText
        }
        val datePicker = DatePickerDialog(this,listener,year,month,day)
        datePicker.show()
    }


    @Override
    override fun onBackPressed() {
        return;
    }
}

