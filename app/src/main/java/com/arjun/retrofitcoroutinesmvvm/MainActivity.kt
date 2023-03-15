package com.arjun.retrofitcoroutinesmvvm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arjun.retrofitcoroutinesmvvm.api.QuoteService
import com.arjun.retrofitcoroutinesmvvm.api.RetrofitHelper
import com.arjun.retrofitcoroutinesmvvm.repository.QuotesRepository
import com.arjun.retrofitcoroutinesmvvm.viewModel.MainViewModel
import com.arjun.retrofitcoroutinesmvvm.viewModel.MainViewModelFactory

class MainActivity : AppCompatActivity() {

    lateinit var mainViewModel: MainViewModel
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val quoteService = RetrofitHelper.getInstance().create(QuoteService::class.java)
        val repository = QuotesRepository(quoteService)

        mainViewModel = ViewModelProvider(this,MainViewModelFactory(repository)).get(MainViewModel::class.java)

        recyclerView = findViewById(R.id.recyclerView)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        mainViewModel.quotes.observe(this){
            // SetUp Adapter --->
            val newAdapter = Adapter(applicationContext, it.results)
            recyclerView.adapter = newAdapter
            newAdapter.notifyDataSetChanged()
        }

    }
}