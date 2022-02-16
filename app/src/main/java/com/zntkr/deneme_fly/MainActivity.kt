package com.zntkr.deneme_fly

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zntkr.deneme_fly.adapter.RecyclerViewAdapter
import com.zntkr.deneme_fly.databinding.ActivityMainBinding
import com.zntkr.deneme_fly.model.FlyModel
import com.zntkr.deneme_fly.service.QApi
import kotlinx.coroutines.*
import retrofit2.Retrofit

class MainActivity : AppCompatActivity() {
    private var pageNumber = 1
    private var flyModels: FlyModel? = null
    private var recyclerViewAdapter : RecyclerViewAdapter? = null
    private lateinit var binding : ActivityMainBinding
    private var job : Job? = null

    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println("Error: ${throwable.localizedMessage}")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val quotesApi = RetrofitHelper.getInstance().create(QApi::class.java)
//        // launching a new coroutine
//        GlobalScope.launch {
//            val result = quotesApi.getData()
//            Log.d("ayush: ", result.body()?.flights?.get(0)?.flightName!!)
//        }

        val layoutManager : RecyclerView.LayoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager

        loadData()

        binding.fab.setOnClickListener {
            pageNumber += 1
            loadData()
        }
        binding.fab2.setOnClickListener {
            if(pageNumber > 0){
                pageNumber -= 1
                loadData()
            }
        }
    }

    private fun loadData() {
        val retrofit = RetrofitHelper.getInstance().create(QApi::class.java)

        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = retrofit.getData(page = pageNumber)

            withContext(Dispatchers.Main){
                if(response.isSuccessful){
                    response.body()?.let {
                        flyModels = it
                        flyModels?.let {
                            recyclerViewAdapter = RecyclerViewAdapter(it)
                            binding.recyclerView.adapter = recyclerViewAdapter
                        }
                    }
                }
            }
        }
    }
}