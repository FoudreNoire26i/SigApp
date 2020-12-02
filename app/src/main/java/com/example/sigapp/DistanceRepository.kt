package com.example.sigapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sigapp.API.ApiFactory
import com.example.sigapp.JsonModelClass.ParcDistance
import com.example.sigapp.JsonModelClass.RouteDetail
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object DistanceRepository {
    private val api = ApiFactory.myAPI
    var parcDistanceLiveData = MutableLiveData<ParcDistance>()

    fun getparcDistance(): LiveData<ParcDistance> {
        Log.e("enque", "go")
        api.getParcDistance().enqueue(object : Callback<ParcDistance> {
            override fun onFailure(call: Call<ParcDistance>, t: Throwable) {
                Log.e("on Failure :", "distance get error/"+t.message)
            }

            override fun onResponse(call: Call<ParcDistance>, response: Response<ParcDistance>) {
                Log.e("distance get", "nice")
                response.body()?.let { parcDistanceLiveData.postValue(it) }
            }
        })
        return parcDistanceLiveData
    }
}