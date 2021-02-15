package com.example.sigapp

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getColor
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.example.sigapp.JsonModelClass.Distance
import com.example.sigapp.JsonModelClass.ParcPoint
import com.example.sigapp.JsonModelClass.ParcRoute
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {



    companion object {
        private lateinit var metier : Metier
    }


    private lateinit var labelRoutes: TextView
    lateinit var imageView: ImageView
    lateinit var buttonRefresh: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonRefresh = findViewById<Button>(R.id.tracage)

        val points = resources.getStringArray(R.array.points)
        val spinner = findViewById<Spinner>(R.id.depart)
        if (spinner != null) {
            val adapter = ArrayAdapter(this,
                    android.R.layout.simple_spinner_item, points)
            spinner.adapter = adapter
        }

        val spinner2 = findViewById<Spinner>(R.id.arrive)
        if (spinner2 != null) {
            val adapter = ArrayAdapter(this,
                    android.R.layout.simple_spinner_item, points)
            spinner2.adapter = adapter
        }


        metier = Metier(this)

        imageView = findViewById(R.id.imageView)
        val bitmap = Bitmap.createBitmap(windowManager.defaultDisplay.width , windowManager.defaultDisplay.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        canvas.drawColor(getColor(this,R.color.grey))


        DistanceRepository.getparcDistance().observe(this, Observer {
            Log.e("observe dist", ""+it.retourDistance.get(0).origine)
        })


        val paint = Paint()
        paint.color = Color.WHITE
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 8F
        paint.isAntiAlias = true

        // dessin des routes
        val paintRoutes = Paint()
        paintRoutes.color = getColor(this,R.color.routes)
        paintRoutes.style = Paint.Style.STROKE
        paintRoutes.strokeWidth = 8F
        paintRoutes.isAntiAlias = true
        drawRoutes(canvas,paintRoutes)

        //dessin point
        val paintPoint = Paint()
        paintPoint.color = getColor(this,R.color.point)
        paintPoint.isAntiAlias = true
        paintPoint.strokeWidth = 60F
        paintPoint.strokeCap = Paint.Cap.ROUND
        paintPoint.isLinearText = true
        drawPoints(canvas,paintPoint)

        // dessin lettre
        val paintText = Paint()
        paintText.color = getColor(this,R.color.lettre)
        paintText.isLinearText = true
        paintText.textSize = 70F
        drawLetters(canvas,paintText)


        Log.d("width",windowManager.defaultDisplay.width.toString())
        Log.d("width",windowManager.defaultDisplay.height.toString())


        drawRoutes(canvas,paint)
        drawPoints(canvas,paintPoint)
        imageView.setImageBitmap(bitmap)

        metier.myPath.observe(this, Observer {
            drawPath(it, canvas)
            drawPoints(canvas, paintPoint)
            imageView.setImageBitmap(bitmap)
        })


        ParcRepository.getParc().observe(this, Observer {parc ->
            drawRedPath(parc.myParc[1].listRoutes,canvas)
            drawPoints(canvas, paintPoint)
        })

        imageView.setImageBitmap(bitmap)

        buttonRefresh.setOnClickListener {
            drawRoutes(canvas, paint)
            drawRedPath(metier.listRoute, canvas)
            metier.drawPath(spinner.selectedItem.toString(), spinner2.selectedItem.toString())
        }

    }

    private fun drawRedPath(myRoadsList: List<ParcRoute>,canvas: Canvas) {

        val paintRoutesRed = Paint()
        paintRoutesRed.color = Color.RED
        paintRoutesRed.style = Paint.Style.STROKE
        paintRoutesRed.strokeWidth = 8F
        paintRoutesRed.isAntiAlias = true

        myRoadsList.forEach {
            if(it.mobileId!="0"){
                when(it.routeId){
                    "1"->        canvas.drawLine((canvas.width/4).toFloat(),(canvas.height/7-15).toFloat(),2*(canvas.width/4).toFloat(),(canvas.height/7-15).toFloat(),paintRoutesRed)
                    "2"->        canvas.drawLine((canvas.width/4).toFloat(),(canvas.height/7+15).toFloat(),2*(canvas.width/4).toFloat(),(canvas.height/7+15).toFloat(),paintRoutesRed)
                    "3"->        canvas.drawLine((2*canvas.width/4-20).toFloat(),(canvas.height/7).toFloat(),2*(canvas.width/4-10).toFloat(),(2*canvas.height/7).toFloat(),paintRoutesRed)
                    "4"->        canvas.drawLine((2*canvas.width/4+20).toFloat(),(canvas.height/7).toFloat(),2*(canvas.width/4+10).toFloat(),(2*canvas.height/7).toFloat(),paintRoutesRed)
                    "5"->        canvas.drawLine((canvas.width/4-20).toFloat(),(canvas.height/7).toFloat(),2*(canvas.width/4-10).toFloat(),(2*canvas.height/7).toFloat(),paintRoutesRed)
                    "6"->        canvas.drawLine((canvas.width/4+20).toFloat(),(canvas.height/7).toFloat(),2*(canvas.width/4+10).toFloat(),(2*canvas.height/7).toFloat(),paintRoutesRed)
                    "7"->        canvas.drawLine((1*canvas.width/4-20).toFloat(),(canvas.height/7).toFloat(),1*(canvas.width/4-20).toFloat(),(2*canvas.height/7).toFloat(),paintRoutesRed)
                    "8"->        canvas.drawLine((1*canvas.width/4+20).toFloat(),(canvas.height/7).toFloat(),1*(canvas.width/4+20).toFloat(),(2*canvas.height/7).toFloat(),paintRoutesRed)
                    "9"->        canvas.drawLine((2*canvas.width/4+20).toFloat(),(2*canvas.height/7).toFloat(),3*(canvas.width/4+7).toFloat(),(3*canvas.height/7).toFloat(),paintRoutesRed)
                    "10"->       canvas.drawLine((2*canvas.width/4-20).toFloat(),(2*canvas.height/7).toFloat(),3*(canvas.width/4-7).toFloat(),(3*canvas.height/7).toFloat(),paintRoutesRed)
                    "11"->       canvas.drawLine((1*canvas.width/4-15).toFloat(),(2*canvas.height/7).toFloat(),(0.3*canvas.width/4-15).toFloat(),(5*canvas.height/7).toFloat(),paintRoutesRed)
                    "13"->       canvas.drawLine((1*canvas.width/4).toFloat(),(2*canvas.height/7).toFloat(),2*(canvas.width/4).toFloat(),(3*canvas.height/7).toFloat(),paintRoutesRed)
                    "15"->       canvas.drawLine((1*canvas.width/4).toFloat(),(4*canvas.height/7).toFloat(),2*(canvas.width/4).toFloat(),(3*canvas.height/7).toFloat(),paintRoutesRed)
                    "16"->       canvas.drawLine((1*canvas.width/4+15).toFloat(),(2*canvas.height/7).toFloat(),(0.3*canvas.width/4+15).toFloat(),(5*canvas.height/7).toFloat(),paintRoutesRed)
                    "18"->       canvas.drawLine((canvas.width/4).toFloat(),(4*canvas.height/7).toFloat(),(0.3*canvas.width/4).toFloat(),(5*canvas.height/7).toFloat(),paintRoutesRed)
                    "19"->       canvas.drawLine((1*canvas.width/4).toFloat(),(5*canvas.height/7).toFloat(),(0.3*canvas.width/4).toFloat(),(5*canvas.height/7).toFloat(),paintRoutesRed)
                    "20"->       canvas.drawLine((1*canvas.width/4).toFloat(),(4*canvas.height/7).toFloat(),1*(canvas.width/4).toFloat(),(5*canvas.height/7).toFloat(),paintRoutesRed)
                    "21"->       canvas.drawLine((1*canvas.width/4).toFloat(),(4*canvas.height/7-15).toFloat(),2*(canvas.width/4).toFloat(),(4*canvas.height/7-15).toFloat(),paintRoutesRed)
                    "22"->       canvas.drawLine((1*canvas.width/4).toFloat(),(4*canvas.height/7+15).toFloat(),2*(canvas.width/4).toFloat(),(4*canvas.height/7+15).toFloat(),paintRoutesRed)
                    "23"->       canvas.drawLine((canvas.width/4-20).toFloat(),(2*canvas.height/7).toFloat(),(canvas.width/4-20).toFloat(),(3*canvas.height/7).toFloat(),paintRoutesRed)
                    "24"->       canvas.drawLine((canvas.width/4+20).toFloat(),(2*canvas.height/7).toFloat(),(canvas.width/4+20).toFloat(),(3*canvas.height/7).toFloat(),paintRoutesRed)
                    "26"->       canvas.drawLine((1*canvas.width/4-20).toFloat(),(3*canvas.height/7).toFloat(),2*(canvas.width/4-10).toFloat(),(5*canvas.height/7).toFloat(),paintRoutesRed)
                    "27"->       canvas.drawLine((1*canvas.width/4+20).toFloat(),(3*canvas.height/7).toFloat(),2*(canvas.width/4+10).toFloat(),(5*canvas.height/7).toFloat(),paintRoutesRed)
                    "28"->       canvas.drawLine((1*canvas.width/4).toFloat(),(5*canvas.height/7).toFloat(),2*(canvas.width/4).toFloat(),(6*canvas.height/7).toFloat(),paintRoutesRed)
                    "29"->       canvas.drawLine((2*canvas.width/4).toFloat(),(6*canvas.height/7).toFloat(),2*(canvas.width/4).toFloat(),(5*canvas.height/7).toFloat(),paintRoutesRed)
                    "30"->       canvas.drawLine((2*canvas.width/4-20).toFloat(),(6*canvas.height/7).toFloat(),3*(canvas.width/4-7).toFloat(),(5*canvas.height/7).toFloat(),paintRoutesRed)
                    "31"->       canvas.drawLine((2*canvas.width/4+20).toFloat(),(6*canvas.height/7).toFloat(),3*(canvas.width/4+7).toFloat(),(5*canvas.height/7).toFloat(),paintRoutesRed)
                    "33"->       canvas.drawLine((3*canvas.width/4-20).toFloat(),(3*canvas.height/7).toFloat(),3*(canvas.width/4-7).toFloat(),(4*canvas.height/7).toFloat(),paintRoutesRed)
                    "34"->       canvas.drawLine((3*canvas.width/4+20).toFloat(),(3*canvas.height/7).toFloat(),3*(canvas.width/4+7).toFloat(),(4*canvas.height/7).toFloat(),paintRoutesRed)
                    "35"->       canvas.drawLine((3*canvas.width/4).toFloat(),(4*canvas.height/7).toFloat(),3*(canvas.width/4).toFloat(),(5*canvas.height/7).toFloat(),paintRoutesRed)
                    "37"->       canvas.drawLine((2*canvas.width/4-20).toFloat(),(4*canvas.height/7).toFloat(),3*(canvas.width/4-7).toFloat(),(5*canvas.height/7).toFloat(),paintRoutesRed)
                    "38"->       canvas.drawLine((2*canvas.width/4+20).toFloat(),(4*canvas.height/7).toFloat(),3*(canvas.width/4+7).toFloat(),(5*canvas.height/7).toFloat(),paintRoutesRed)

                }
            }
        }
    }


    private fun drawPath(myRoadsList: List<ParcRoute>,canvas: Canvas) {

        val paintRoutesRed = Paint()
        paintRoutesRed.color = Color.YELLOW
        paintRoutesRed.style = Paint.Style.STROKE
        paintRoutesRed.strokeWidth = 8F
        paintRoutesRed.isAntiAlias = true

        myRoadsList.forEach {
            when(it.routeId){
                "1"->        canvas.drawLine((canvas.width/4).toFloat(),(canvas.height/7-15).toFloat(),2*(canvas.width/4).toFloat(),(canvas.height/7-15).toFloat(),paintRoutesRed)
                "2"->        canvas.drawLine((canvas.width/4).toFloat(),(canvas.height/7+15).toFloat(),2*(canvas.width/4).toFloat(),(canvas.height/7+15).toFloat(),paintRoutesRed)
                "3"->        canvas.drawLine((2*canvas.width/4-20).toFloat(),(canvas.height/7).toFloat(),2*(canvas.width/4-10).toFloat(),(2*canvas.height/7).toFloat(),paintRoutesRed)
                "4"->        canvas.drawLine((2*canvas.width/4+20).toFloat(),(canvas.height/7).toFloat(),2*(canvas.width/4+10).toFloat(),(2*canvas.height/7).toFloat(),paintRoutesRed)
                "5"->        canvas.drawLine((canvas.width/4-20).toFloat(),(canvas.height/7).toFloat(),2*(canvas.width/4-10).toFloat(),(2*canvas.height/7).toFloat(),paintRoutesRed)
                "6"->        canvas.drawLine((canvas.width/4+20).toFloat(),(canvas.height/7).toFloat(),2*(canvas.width/4+10).toFloat(),(2*canvas.height/7).toFloat(),paintRoutesRed)
                "7"->        canvas.drawLine((1*canvas.width/4-20).toFloat(),(canvas.height/7).toFloat(),1*(canvas.width/4-20).toFloat(),(2*canvas.height/7).toFloat(),paintRoutesRed)
                "8"->        canvas.drawLine((1*canvas.width/4+20).toFloat(),(canvas.height/7).toFloat(),1*(canvas.width/4+20).toFloat(),(2*canvas.height/7).toFloat(),paintRoutesRed)
                "9"->        canvas.drawLine((2*canvas.width/4+20).toFloat(),(2*canvas.height/7).toFloat(),3*(canvas.width/4+7).toFloat(),(3*canvas.height/7).toFloat(),paintRoutesRed)
                "10"->       canvas.drawLine((2*canvas.width/4-20).toFloat(),(2*canvas.height/7).toFloat(),3*(canvas.width/4-7).toFloat(),(3*canvas.height/7).toFloat(),paintRoutesRed)
                "11"->       canvas.drawLine((1*canvas.width/4-15).toFloat(),(2*canvas.height/7).toFloat(),(0.3*canvas.width/4-15).toFloat(),(5*canvas.height/7).toFloat(),paintRoutesRed)
                "13"->       canvas.drawLine((1*canvas.width/4).toFloat(),(2*canvas.height/7).toFloat(),2*(canvas.width/4).toFloat(),(3*canvas.height/7).toFloat(),paintRoutesRed)
                "15"->       canvas.drawLine((1*canvas.width/4).toFloat(),(4*canvas.height/7).toFloat(),2*(canvas.width/4).toFloat(),(3*canvas.height/7).toFloat(),paintRoutesRed)
                "16"->       canvas.drawLine((1*canvas.width/4+15).toFloat(),(2*canvas.height/7).toFloat(),(0.3*canvas.width/4+15).toFloat(),(5*canvas.height/7).toFloat(),paintRoutesRed)
                "18"->       canvas.drawLine((canvas.width/4).toFloat(),(4*canvas.height/7).toFloat(),(0.3*canvas.width/4).toFloat(),(5*canvas.height/7).toFloat(),paintRoutesRed)
                "19"->       canvas.drawLine((1*canvas.width/4).toFloat(),(5*canvas.height/7).toFloat(),(0.3*canvas.width/4).toFloat(),(5*canvas.height/7).toFloat(),paintRoutesRed)
                "20"->       canvas.drawLine((1*canvas.width/4).toFloat(),(4*canvas.height/7).toFloat(),1*(canvas.width/4).toFloat(),(5*canvas.height/7).toFloat(),paintRoutesRed)
                "21"->       canvas.drawLine((1*canvas.width/4).toFloat(),(4*canvas.height/7-15).toFloat(),2*(canvas.width/4).toFloat(),(4*canvas.height/7-15).toFloat(),paintRoutesRed)
                "22"->       canvas.drawLine((1*canvas.width/4).toFloat(),(4*canvas.height/7+15).toFloat(),2*(canvas.width/4).toFloat(),(4*canvas.height/7+15).toFloat(),paintRoutesRed)
                "23"->       canvas.drawLine((canvas.width/4-20).toFloat(),(2*canvas.height/7).toFloat(),(canvas.width/4-20).toFloat(),(3*canvas.height/7).toFloat(),paintRoutesRed)
                "24"->       canvas.drawLine((canvas.width/4+20).toFloat(),(2*canvas.height/7).toFloat(),(canvas.width/4+20).toFloat(),(3*canvas.height/7).toFloat(),paintRoutesRed)
                "26"->       canvas.drawLine((1*canvas.width/4-20).toFloat(),(3*canvas.height/7).toFloat(),2*(canvas.width/4-10).toFloat(),(5*canvas.height/7).toFloat(),paintRoutesRed)
                "27"->       canvas.drawLine((1*canvas.width/4+20).toFloat(),(3*canvas.height/7).toFloat(),2*(canvas.width/4+10).toFloat(),(5*canvas.height/7).toFloat(),paintRoutesRed)
                "28"->       canvas.drawLine((1*canvas.width/4).toFloat(),(5*canvas.height/7).toFloat(),2*(canvas.width/4).toFloat(),(6*canvas.height/7).toFloat(),paintRoutesRed)
                "29"->       canvas.drawLine((2*canvas.width/4).toFloat(),(6*canvas.height/7).toFloat(),2*(canvas.width/4).toFloat(),(5*canvas.height/7).toFloat(),paintRoutesRed)
                "30"->       canvas.drawLine((2*canvas.width/4-20).toFloat(),(6*canvas.height/7).toFloat(),3*(canvas.width/4-7).toFloat(),(5*canvas.height/7).toFloat(),paintRoutesRed)
                "31"->       canvas.drawLine((2*canvas.width/4+20).toFloat(),(6*canvas.height/7).toFloat(),3*(canvas.width/4+7).toFloat(),(5*canvas.height/7).toFloat(),paintRoutesRed)
                "33"->       canvas.drawLine((3*canvas.width/4-20).toFloat(),(3*canvas.height/7).toFloat(),3*(canvas.width/4-7).toFloat(),(4*canvas.height/7).toFloat(),paintRoutesRed)
                "34"->       canvas.drawLine((3*canvas.width/4+20).toFloat(),(3*canvas.height/7).toFloat(),3*(canvas.width/4+7).toFloat(),(4*canvas.height/7).toFloat(),paintRoutesRed)
                "35"->       canvas.drawLine((3*canvas.width/4).toFloat(),(4*canvas.height/7).toFloat(),3*(canvas.width/4).toFloat(),(5*canvas.height/7).toFloat(),paintRoutesRed)
                "37"->       canvas.drawLine((2*canvas.width/4-20).toFloat(),(4*canvas.height/7).toFloat(),3*(canvas.width/4-7).toFloat(),(5*canvas.height/7).toFloat(),paintRoutesRed)
                "38"->       canvas.drawLine((2*canvas.width/4+20).toFloat(),(4*canvas.height/7).toFloat(),3*(canvas.width/4+7).toFloat(),(5*canvas.height/7).toFloat(),paintRoutesRed)

            }
        }
    }

    fun dijkstra(listPoint: List<ParcPoint>, listRoute: List<ParcRoute>, dep : ParcPoint, arr : ParcPoint, dist : Distance) : List<ParcRoute>{
        var pred = emptyMap<ParcPoint, String>().toMutableMap()
        var l = emptyMap<ParcPoint, Double>().toMutableMap()
        var E = emptyList<ParcPoint>().toMutableList()

        listPoint.forEach {
            pred[it] = "0"
            l[it] = 5000.0
        }
        l[dep] = 0.0
        E.add(dep)
        var s = dep

        while (E.count() > 0 && s.id != arr.id){
            s = E.first()
            E.forEach {
                if ( l[s]!! > l[it]!!){
                    s = it
                }
            }
            E.remove(s)
            var myRoutes = listRoute.filter { it.pointIdDebut == s.id }
            var mySuccesseurs = emptyList<ParcPoint>().toMutableList()
            myRoutes.forEach { route ->
                mySuccesseurs.add(listPoint.first { it.id == route.pointIdFin })
            }
            mySuccesseurs.forEach {
                if (l[it]!! > l[s]!!+ s.getPoids(dist, it)){
                    l[it] = l[s]!!+ s.getPoids(dist, it)
                    pred[it] = s.id
                    E.add(it)
                }
            }
        }
        // todo : renvoyer un truc propre a combaz

        var results = emptyList<ParcRoute>().toMutableList()

        var pointId = arr.id
        while (pointId != dep.id){
            Log.e("route", "debut : ${pointId}, fin : ${pred[listPoint.first { it.id == pointId}]}")
            results.add(listRoute.first { route -> route.pointIdFin == pointId && route.pointIdDebut == pred[listPoint.first { it.id == pointId}] })
            pointId = pred[listPoint.first { it.id == pointId}] ?: "0"
        }
        results.reversed().forEach { Log.e("route a prendre", it.routeId) }
/*
        listPoint.forEach {
            Log.e("point ${it.nom}",  ": ${pred[it]}")
        }
*/
        return results
    }

    private fun drawLetters(canvas: Canvas, paintText: Paint){
        canvas.drawText("K",(0.70*canvas.width/4).toFloat(),(0.90*canvas.height/7).toFloat(),paintText)
        canvas.drawText("A",(2.10*canvas.width/4).toFloat(),(0.90*canvas.height/7).toFloat(),paintText)
        canvas.drawText("B",(0.70*canvas.width/4).toFloat(),(1.90*canvas.height/7).toFloat(),paintText)
        canvas.drawText("C",(2.10*canvas.width/4).toFloat(),(1.90*canvas.height/7).toFloat(),paintText)
        canvas.drawText("G",(1.10*canvas.width/4).toFloat(),(2.90*canvas.height/7).toFloat(),paintText)
        canvas.drawText("J",(2.20*canvas.width/4).toFloat(),(2.90*canvas.height/7).toFloat(),paintText)
        canvas.drawText("F",(3.10*canvas.width/4).toFloat(),(2.90*canvas.height/7).toFloat(),paintText)
        canvas.drawText("I",(0.80*canvas.width/4).toFloat(),(3.90*canvas.height/7).toFloat(),paintText)
        canvas.drawText("O",(2.10*canvas.width/4).toFloat(),(3.90*canvas.height/7).toFloat(),paintText)
        canvas.drawText("E",(3.10*canvas.width/4).toFloat(),(3.90*canvas.height/7).toFloat(),paintText)
        canvas.drawText("L",(0.10*canvas.width/4).toFloat(),(4.90*canvas.height/7).toFloat(),paintText)
        canvas.drawText("H",(0.70*canvas.width/4).toFloat(),(4.90*canvas.height/7).toFloat(),paintText)
        canvas.drawText("N",(2.10*canvas.width/4).toFloat(),(4.90*canvas.height/7).toFloat(),paintText)
        canvas.drawText("D",(3.10*canvas.width/4).toFloat(),(4.90*canvas.height/7).toFloat(),paintText)
        canvas.drawText("M",(1.90*canvas.width/4).toFloat(),(6.40*canvas.height/7).toFloat(),paintText)
    }

    private fun drawPoints(canvas: Canvas,paintPoint: Paint){
        canvas.drawPoint((1*canvas.width/4).toFloat(),(1*canvas.height/7).toFloat(),paintPoint)
        canvas.drawPoint((1*canvas.width/4).toFloat(),(2*canvas.height/7).toFloat(),paintPoint)
        canvas.drawPoint((1*canvas.width/4).toFloat(),(3*canvas.height/7).toFloat(),paintPoint)
        canvas.drawPoint((1*canvas.width/4).toFloat(),(4*canvas.height/7).toFloat(),paintPoint)
        canvas.drawPoint((1*canvas.width/4).toFloat(),(5*canvas.height/7).toFloat(),paintPoint)
        canvas.drawPoint((2*canvas.width/4).toFloat(),(1*canvas.height/7).toFloat(),paintPoint)
        canvas.drawPoint((2*canvas.width/4).toFloat(),(2*canvas.height/7).toFloat(),paintPoint)
        canvas.drawPoint((2*canvas.width/4).toFloat(),(3*canvas.height/7).toFloat(),paintPoint)
        canvas.drawPoint((2*canvas.width/4).toFloat(),(4*canvas.height/7).toFloat(),paintPoint)
        canvas.drawPoint((0.3*canvas.width/4).toFloat(),(5*canvas.height/7).toFloat(),paintPoint)
        canvas.drawPoint((2*canvas.width/4).toFloat(),(6*canvas.height/7).toFloat(),paintPoint)
        canvas.drawPoint((3*canvas.width/4).toFloat(),(3*canvas.height/7).toFloat(),paintPoint)
        canvas.drawPoint((3*canvas.width/4).toFloat(),(4*canvas.height/7).toFloat(),paintPoint)
        canvas.drawPoint((3*canvas.width/4).toFloat(),(5*canvas.height/7).toFloat(),paintPoint)
        canvas.drawPoint((2*canvas.width/4).toFloat(),(5*canvas.height/7).toFloat(),paintPoint)
    }

    private fun drawRoutes(canvas: Canvas, paint: Paint) {
        //KA
        canvas.drawLine((canvas.width/4).toFloat(),(canvas.height/7-15).toFloat(),2*(canvas.width/4).toFloat(),(canvas.height/7-15).toFloat(),paint)
        canvas.drawLine((canvas.width/4).toFloat(),(canvas.height/7+15).toFloat(),2*(canvas.width/4).toFloat(),(canvas.height/7+15).toFloat(),paint)
        //IO
        canvas.drawLine((1*canvas.width/4).toFloat(),(4*canvas.height/7-15).toFloat(),2*(canvas.width/4).toFloat(),(4*canvas.height/7-15).toFloat(),paint)
        canvas.drawLine((1*canvas.width/4).toFloat(),(4*canvas.height/7+15).toFloat(),2*(canvas.width/4).toFloat(),(4*canvas.height/7+15).toFloat(),paint)
        //AC
        canvas.drawLine((2*canvas.width/4-20).toFloat(),(canvas.height/7).toFloat(),2*(canvas.width/4-10).toFloat(),(2*canvas.height/7).toFloat(),paint)
        canvas.drawLine((2*canvas.width/4+20).toFloat(),(canvas.height/7).toFloat(),2*(canvas.width/4+10).toFloat(),(2*canvas.height/7).toFloat(),paint)
        //KC
        canvas.drawLine((canvas.width/4-20).toFloat(),(canvas.height/7).toFloat(),2*(canvas.width/4-10).toFloat(),(2*canvas.height/7).toFloat(),paint)
        canvas.drawLine((canvas.width/4+20).toFloat(),(canvas.height/7).toFloat(),2*(canvas.width/4+10).toFloat(),(2*canvas.height/7).toFloat(),paint)
        //KB
        canvas.drawLine((1*canvas.width/4-20).toFloat(),(canvas.height/7).toFloat(),1*(canvas.width/4-20).toFloat(),(2*canvas.height/7).toFloat(),paint)
        canvas.drawLine((1*canvas.width/4+20).toFloat(),(canvas.height/7).toFloat(),1*(canvas.width/4+20).toFloat(),(2*canvas.height/7).toFloat(),paint)
        //BG
        canvas.drawLine((canvas.width/4-20).toFloat(),(2*canvas.height/7).toFloat(),(canvas.width/4-20).toFloat(),(3*canvas.height/7).toFloat(),paint)
        canvas.drawLine((canvas.width/4+20).toFloat(),(2*canvas.height/7).toFloat(),(canvas.width/4+20).toFloat(),(3*canvas.height/7).toFloat(),paint)
        //BL
        canvas.drawLine((1*canvas.width/4-15).toFloat(),(2*canvas.height/7).toFloat(),(0.3*canvas.width/4-15).toFloat(),(5*canvas.height/7).toFloat(),paint)
        canvas.drawLine((1*canvas.width/4+15).toFloat(),(2*canvas.height/7).toFloat(),(0.3*canvas.width/4+15).toFloat(),(5*canvas.height/7).toFloat(),paint)
        //FE
        canvas.drawLine((3*canvas.width/4-20).toFloat(),(3*canvas.height/7).toFloat(),3*(canvas.width/4-7).toFloat(),(4*canvas.height/7).toFloat(),paint)
        canvas.drawLine((3*canvas.width/4+20).toFloat(),(3*canvas.height/7).toFloat(),3*(canvas.width/4+7).toFloat(),(4*canvas.height/7).toFloat(),paint)
        //DM
        canvas.drawLine((2*canvas.width/4-20).toFloat(),(6*canvas.height/7).toFloat(),3*(canvas.width/4-7).toFloat(),(5*canvas.height/7).toFloat(),paint)
        canvas.drawLine((2*canvas.width/4+20).toFloat(),(6*canvas.height/7).toFloat(),3*(canvas.width/4+7).toFloat(),(5*canvas.height/7).toFloat(),paint)
        //GN
        canvas.drawLine((1*canvas.width/4-20).toFloat(),(3*canvas.height/7).toFloat(),2*(canvas.width/4-10).toFloat(),(5*canvas.height/7).toFloat(),paint)
        canvas.drawLine((1*canvas.width/4+20).toFloat(),(3*canvas.height/7).toFloat(),2*(canvas.width/4+10).toFloat(),(5*canvas.height/7).toFloat(),paint)
        //OD
        canvas.drawLine((2*canvas.width/4-20).toFloat(),(4*canvas.height/7).toFloat(),3*(canvas.width/4-7).toFloat(),(5*canvas.height/7).toFloat(),paint)
        canvas.drawLine((2*canvas.width/4+20).toFloat(),(4*canvas.height/7).toFloat(),3*(canvas.width/4+7).toFloat(),(5*canvas.height/7).toFloat(),paint)
        //IL
        canvas.drawLine((canvas.width/4).toFloat(),(4*canvas.height/7).toFloat(),(0.3*canvas.width/4).toFloat(),(5*canvas.height/7).toFloat(),paint)
        //ED
        canvas.drawLine((3*canvas.width/4).toFloat(),(4*canvas.height/7).toFloat(),3*(canvas.width/4).toFloat(),(5*canvas.height/7).toFloat(),paint)
        //HM
        canvas.drawLine((1*canvas.width/4).toFloat(),(5*canvas.height/7).toFloat(),2*(canvas.width/4).toFloat(),(6*canvas.height/7).toFloat(),paint)
        //CF
        canvas.drawLine((2*canvas.width/4+20).toFloat(),(2*canvas.height/7).toFloat(),3*(canvas.width/4+7).toFloat(),(3*canvas.height/7).toFloat(),paint)
        canvas.drawLine((2*canvas.width/4-20).toFloat(),(2*canvas.height/7).toFloat(),3*(canvas.width/4-7).toFloat(),(3*canvas.height/7).toFloat(),paint)
        //BJ
        canvas.drawLine((1*canvas.width/4).toFloat(),(2*canvas.height/7).toFloat(),2*(canvas.width/4).toFloat(),(3*canvas.height/7).toFloat(),paint)
        //IH
        canvas.drawLine((1*canvas.width/4).toFloat(),(4*canvas.height/7).toFloat(),1*(canvas.width/4).toFloat(),(5*canvas.height/7).toFloat(),paint)
        //LH
        canvas.drawLine((1*canvas.width/4).toFloat(),(5*canvas.height/7).toFloat(),(0.3*canvas.width/4).toFloat(),(5*canvas.height/7).toFloat(),paint)
        //NM
        canvas.drawLine((2*canvas.width/4).toFloat(),(6*canvas.height/7).toFloat(),2*(canvas.width/4).toFloat(),(5*canvas.height/7).toFloat(),paint)
        //JI
        canvas.drawLine((1*canvas.width/4).toFloat(),(4*canvas.height/7).toFloat(),2*(canvas.width/4).toFloat(),(3*canvas.height/7).toFloat(),paint)

    }
}