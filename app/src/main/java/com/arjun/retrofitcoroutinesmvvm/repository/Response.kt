package com.arjun.retrofitcoroutinesmvvm.repository

sealed class Response<T> (val errorMessage: T? = null, val data : T? = null) {
    class Success<T>(data: T? = null) : Response<T>(data = data)
    class Error<T>(errorMessage: T?= null) : Response<T>(errorMessage)
    class Loading<T> : Response<T>()
}
