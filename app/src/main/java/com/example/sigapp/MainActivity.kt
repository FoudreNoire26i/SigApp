package com.example.sigapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.Observer
import com.example.sigapp.JsonModelClass.Distance
import com.example.sigapp.JsonModelClass.ParcPoint
import com.example.sigapp.JsonModelClass.ParcRoute

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        DistanceRepository.getparcDistance().observe(this, Observer { dist ->
            ParcRepository.getParc().observe(this, Observer {//le observe est dans le observe car il faut que les parcs soient chargés pour avoir les points
                ParcRepository.getPoints().observe(this, Observer { points ->
                    ParcRepository.getRoutes().observe(this, {
                        Log.e("dijkstra", "startPoint : ${points.first().nom} et endPoint : ${points.get(7).nom}")
                        dijkstra(points, it, points.first(), points.get(7), dist.retourDistance.get(0))
                    })
                })
            })
            Log.e("observe dist", ""+dist.retourDistance.get(0).origine)
        })



        RouteRepository.getRouteDetail(route = 1,mobile = 270).observe(this, Observer {
            findViewById<TextView>(R.id.text).text = it.retourRoute.get(0).code
        })
    }


    fun dijkstra(listPoint: List<ParcPoint>, listRoute: List<ParcRoute>, dep : ParcPoint, arr : ParcPoint, dist : Distance){
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
                    pred[it] = s.nom
                    E.add(it)
                }
            }
        }
        // todo : renvoyer un truc propre a combaz
/*
        var results = emptyList<ParcRoute>().toMutableList()

        var pointId = arr.nom
        while (pointId != "0"){
            Log.e("route", "debut : ${pointId}, fin : ${pred[listPoint.first { it.nom == pointId}]}")
            results.add(listRoute.first { route -> route.pointIdFin == pointId && route.pointIdDebut == pred[listPoint.first { it.nom == pointId}] })
            pointId = pred[listPoint.first { it.nom == pointId}] ?: "0"
        }
        results.forEach { Log.e("route a prendre", it.routeId) }

        listPoint.forEach {
            Log.e("point ${it.nom}",  ": ${pred[it]}")
        }
*/
    }
/*
    fun getResult(mapPred : Map<ParcPoint, Double>, endPoint : ParcPoint): Map<ParcPoint, Double>{
        val maxRoute = 30

    }
*/
}