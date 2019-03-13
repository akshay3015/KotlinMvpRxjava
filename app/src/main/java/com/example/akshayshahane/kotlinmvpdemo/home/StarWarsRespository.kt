package com.example.akshayshahane.kotlinmvpdemo.home

import com.example.akshayshahane.kotlinmvpdemo.network.API
import io.reactivex.Observable

class StarWarsRespository(val api : API) {

    fun films() : Observable<Response>{

        return api.fetchFilms()
    }
}