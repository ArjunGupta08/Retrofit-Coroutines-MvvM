package com.arjun.retrofitcoroutinesmvvm.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arjun.retrofitcoroutinesmvvm.model.QuoteList
import com.arjun.retrofitcoroutinesmvvm.repository.QuotesRepository
import com.arjun.retrofitcoroutinesmvvm.repository.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repository: QuotesRepository) : ViewModel(){

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getQuotes(1)
        }
    }

    val quotes : LiveData<Response<QuoteList>>
        get() = repository.quotes

}