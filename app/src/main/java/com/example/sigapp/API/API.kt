package com.example.sigapp.API

import com.example.sigapp.JsonModelClass.ParcDistance
import com.example.sigapp.JsonModelClass.RouteDetail
import com.example.sigapp.JsonModelClass.Racin
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface API {
    @GET("parcview")
    fun getParc(
    ) : Call<Racin>

    @GET("parcroute")
    fun getRouteDetail(
        @Query("route") routeId: Int,
        @Query("mobile") mobileId: Int,
    ) : Call<RouteDetail>

    @GET("parcmax")
    fun getParcDistance(
    ) : Call<ParcDistance>
}