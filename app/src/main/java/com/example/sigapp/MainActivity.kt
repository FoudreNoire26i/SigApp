package com.example.sigapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.sigapp.JsonModelClass.Distance
import com.example.sigapp.JsonModelClass.ParcPoint
import com.example.sigapp.JsonModelClass.ParcRoute
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {



    companion object {
        private lateinit var metier : Metier
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        metier = Metier(this)
        Log.e("YO", "3s avant")
        Executors.newSingleThreadScheduledExecutor().schedule({
            Log.e("GOOOO", "Je pars de 1 à 6")
        }, 3, TimeUnit.SECONDS)


        /*

        RouteRepository.getRouteDetail(route = 1,mobile = 270).observe(this, Observer {
            findViewById<TextView>(R.id.text).text = it.retourRoute[0].code
        })*/
    }


}