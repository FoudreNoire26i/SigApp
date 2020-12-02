package com.example.sigapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ParcRepository.getParc().observe(this, Observer {//le observe est dans le observe car il faut que les parcs soient chargés pour avoir les points
            ParcRepository.getPoints().observe(this, Observer {
                Log.e("observe", "on est la")
                }
            )
        })

        RouteRepository.getRouteDetail(route = 1,mobile = 270).observe(this, Observer {
            findViewById<TextView>(R.id.text).text = it.retourRoute.get(0).code
        })

        DistanceRepository.getparcDistance().observe(this, Observer {
            Log.e("observe dist", ""+it.retourDistance.get(0).origine)
        })




    }
}