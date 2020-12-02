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
                findViewById<TextView>(R.id.text).text = it.get(0).toString()
                }
            )
        })




    }
}