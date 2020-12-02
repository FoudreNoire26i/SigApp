package com.example.sigapp

import retrofit2.Call
import retrofit2.http.GET

interface API {
    @GET("parcview")
    fun getParc(
    ) : Call<Racin>
}