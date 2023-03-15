package com.arjun.retrofitcoroutinesmvvm.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.arjun.retrofitcoroutinesmvvm.api.QuoteService
import com.arjun.retrofitcoroutinesmvvm.model.QuoteList

class QuotesRepository(private val quoteService: QuoteService) {

    private val quotesLiveData = MutableLiveData<QuoteList>()

    val quotes : LiveData<QuoteList>
    get() = quotesLiveData

    suspend fun getQuotes(page : Int){
        val result = quoteService.getQuotes(page)
        if (result?.body() != null){
            quotesLiveData.postValue(result.body())
        }
    }
}