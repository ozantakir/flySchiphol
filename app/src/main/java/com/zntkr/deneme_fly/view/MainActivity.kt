package com.zntkr.deneme_fly.view
import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zntkr.deneme_fly.R
import com.zntkr.deneme_fly.adapter.RecyclerViewAdapter
import com.zntkr.deneme_fly.databinding.ActivityMainBinding
import com.zntkr.deneme_fly.model.Destination
import com.zntkr.deneme_fly.model.Flight
import com.zntkr.deneme_fly.viewmodel.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import java.util.*

class MainActivity : AppCompatActivity(),RecyclerViewAdapter.MyOnClickListener {

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
                    recyclerViewAdapter = RecyclerViewAdapter(it,this)
                    binding.recyclerView.adapter = recyclerViewAdapter
                }
            }
        }
    }
    // getting date from calendar
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

    // preventing back button functionality
    @Override
    override fun onBackPressed() {
        return;
    }

    // on click for recycler view row
    override fun onClick(position: Int) {
        val intent = Intent(this,DetailsActivity::class.java)
        // getting time for now
        val y = Calendar.getInstance().get(Calendar.YEAR)
        val m = Calendar.getInstance().get(Calendar.MONTH) + 1
        val d = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        val h = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)

        // time for selected flight
        val flyDate = flyModels?.get(position)?.scheduleDate
        val flyYear = flyDate?.substring(0,4)?.toInt()
        val flyMonth = flyDate?.substring(5,7)?.toInt()
        val flyDay = flyDate?.substring(8,10)?.toInt()

                // comparing flight direction and time to decide if reservation is possible
                if(flyModels?.get(position)?.flightDirection == "D" && y <= flyYear!!){
                    if(m < flyMonth!!){
                            intent.putExtra("reservation","true")
                    } else if(m == flyMonth && d < flyDay!!){
                        intent.putExtra("reservation","true")
                    }
                } else {
                    intent.putExtra("reservation","false")
                }

                // sending data with intent and starting activity
                intent.putExtra("flyName", flyModels?.get(position)?.flightName)
                intent.putExtra("flyNumber", flyModels?.get(position)?.flightNumber.toString())
                intent.putExtra("flyDirection", flyModels?.get(position)?.flightDirection)
                intent.putExtra("flyGate", flyModels?.get(position)?.gate.toString())
                intent.putExtra("flyDate", flyModels?.get(position)?.scheduleDate)
                intent.putExtra("flyTime", flyModels?.get(position)?.scheduleTime)
                intent.putExtra("flyDestination",flyModels?.get(position)?.route?.destinations?.get(0))

                startActivity(intent)
    }
}

