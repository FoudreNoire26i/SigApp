package com.example.sigapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sigapp.API.ApiFactory
import com.example.sigapp.JsonModelClass.ParcPoint
import com.example.sigapp.JsonModelClass.ParcRoute
import com.example.sigapp.JsonModelClass.Racin
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object ParcRepository {
    private val api = ApiFactory.myAPI
    var parcLiveData = MutableLiveData<Racin>()
    var listParcRouteLiveData = MutableLiveData<List<ParcRoute>>()
    var listParcPointLiveData = MutableLiveData<List<ParcPoint>>()


    fun getParc(): LiveData<Racin> {
        Log.e("enque", "go")
        api.getParc().enqueue(object : Callback<Racin> {

            override fun onFailure(call: Call<Racin>, t: Throwable) {
                Log.e("on Failure :", "retrofit error get parcview/"+t.message)
            }

            override fun onResponse(call: Call<Racin>, response: Response<Racin>) {
                Log.e("get parcview success", "yeeah")
                response.body()?.let { parcLiveData.postValue(it) }
            }
        })
        return parcLiveData
    }

    fun getRoutes():LiveData<List<ParcRoute>>{
        if (parcLiveData.value != null) {
            parcLiveData.value?.myParc?.get(1)?.listRoutes?.let{
                listParcRouteLiveData.postValue(it)
            }
            return listParcRouteLiveData
        }
        else {
            listParcRouteLiveData.postValue(emptyList())
            return listParcRouteLiveData
        }
    }

    fun getPoints():LiveData<List<ParcPoint>>{
        if (parcLiveData.value != null) {
            parcLiveData.value?.myParc?.get(0)?.listPoints?.let{
                listParcPointLiveData.postValue(it)
            }
            return listParcPointLiveData
        }
        else {
            listParcPointLiveData.postValue(emptyList())
            return listParcPointLiveData
        }
    }
}