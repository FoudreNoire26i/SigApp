package com.example.sigapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sigapp.API.ApiFactory
import com.example.sigapp.JsonModelClass.Racin
import com.example.sigapp.JsonModelClass.RouteDetail
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object RouteRepository {
    private val api = ApiFactory.myAPI
    var routeDetailLiveData = MutableLiveData<RouteDetail>()

    fun getRouteDetail(route : Int, mobile : Int): LiveData<RouteDetail> {
        Log.e("enque", "go")
        api.getRouteDetail(routeId = route, mobileId = mobile).enqueue(object : Callback<RouteDetail> {
            override fun onFailure(call: Call<RouteDetail>, t: Throwable) {
                Log.e("on Failure :", "route detail get error/"+t.message)
            }

            override fun onResponse(call: Call<RouteDetail>, response: Response<RouteDetail>) {
                response.body()?.let { routeDetailLiveData.postValue(it) }
            }
        })
        return routeDetailLiveData
    }

}