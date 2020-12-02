package com.example.sigapp

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiFactory {
    var gson = GsonBuilder().setLenient().create()

    private fun retrofit() : Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(gson))
        .baseUrl(BASE_URL)
        .build()

    val myAPI : API = retrofit().create(API::class.java)
}