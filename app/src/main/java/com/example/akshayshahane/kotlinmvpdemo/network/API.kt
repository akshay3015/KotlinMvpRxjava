package com.example.akshayshahane.kotlinmvpdemo.network

import com.example.akshayshahane.kotlinmvpdemo.home.Response
import io.reactivex.Observable
import retrofit2.http.GET

interface API {

    @GET("films/")
    fun fetchFilms(): Observable<Response>

}