package com.example.sigapp

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
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


    var paint: Paint = Paint()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        metier = Metier(this)


        paint.setColor(Color.BLACK);

        labelRoutes = findViewById(R.id.textPoints)
        imageView = findViewById(R.id.imageView)


        ParcRepository.getParc().observe(this, Observer {parc ->//le observe est dans le observe car il faut que les parcs soient chargés pour avoir les points
            ParcRepository.getPoints().observe(this, Observer {
                Log.e("observe", "on est la")
                labelRoutes.text = it.toString()
                metier.drawPath(it[0], it[5])
                it.forEach {
                    var parcPoint: ParcPoint = it
                    Log.d("Point ",parcPoint.id)
                }
            })
        })



        val bitmap = Bitmap.createBitmap(windowManager.defaultDisplay.width , windowManager.defaultDisplay.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        canvas.drawColor(Color.BLACK)
        val paint = Paint()
        paint.color = Color.WHITE
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 8F
        paint.isAntiAlias = true

        // dessin des routes
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
        canvas.drawLine((2*canvas.width/4).toFloat(),(2*canvas.height/7).toFloat(),3*(canvas.width/4).toFloat(),(3*canvas.height/7).toFloat(),paint)
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


        val paintPoint = Paint()
        paintPoint.color = Color.CYAN
        paintPoint.isAntiAlias = true
        paintPoint.strokeWidth = 60F
        paintPoint.strokeCap = Paint.Cap.ROUND
        paintPoint.isLinearText = true

        // dessin point

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

        val paintText = Paint()
        paintText.color = Color.MAGENTA
        paintText.isLinearText = true
        paintText.textSize = 70F

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

        imageView.setImageBitmap(bitmap)

        Log.d("width",windowManager.defaultDisplay.width.toString())
        Log.d("width",windowManager.defaultDisplay.height.toString())


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
            results.add(listRoute.first { route -> route.pointIdFin == pointId && route.pointIdDebut == pred[listPoint.first { it.id == pointId}] })
            pointId = pred[listPoint.first { it.id == pointId}] ?: "0"
        }
/*
        listPoint.forEach {
            Log.e("point ${it.nom}",  ": ${pred[it]}")
        }
*/
        return results
    }
}