package com.example.sigapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.sigapp.JsonModelClass.Distance
import com.example.sigapp.JsonModelClass.ParcPoint
import com.example.sigapp.JsonModelClass.ParcRoute
import okhttp3.Route
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.coroutines.coroutineContext

/*
enum class RouteStatus {
    Atttribue,
    Inconnue,
    Incomplete,
    Occupee,
    Bloquee,
    StructPb,
    TerminalAbsent,
    Integrite,
    UpdateError,
    Illegal,
    Sortie,
}*/


class Metier(var context: MainActivity){
    lateinit var dist : Distance
    lateinit var listRoute: List<ParcRoute>
    lateinit var listPoint: List<ParcPoint>
    var myPath = MutableLiveData<List<ParcRoute>>()
    var timeBeforeChangingRoad :Double = 0.0
    //var routeStatus : RouteStatus = RouteStatus.Atttribue

    init {
        DistanceRepository.getparcDistance().observe(context, Observer {
            dist = it.retourDistance[0]
            //Log.e("dijkstra", "startPoint : ${points[2].nom} et endPoint : ${points[4].nom}")
            //dijkstra(points, it, points[2], points[4], dist.retourDistance[0])
            //Log.e("observe dist", ""+ dist.retourDistance[0].origine)
        })
        ParcRepository.getParc().observe(context, Observer {//le observe est dans le observe car il faut que les parcs soient chargés pour avoir les points
            listPoint = it.myParc[0].listPoints
            listRoute = it.myParc[1].listRoutes
        })
    }

    fun drawPath(dep: ParcPoint, arr : ParcPoint) : LiveData<List<ParcRoute>>{
        if (listPoint.isNullOrEmpty() || listRoute.isNullOrEmpty()){
            Log.e("ERROR : E01draw path", "Did you init completely the Metier ?")
            myPath.postValue(emptyList())
            return myPath
        }

        //startPath(expectedPath.first().routeId.toInt(), expectedPath.last().routeId.toInt(), expectedPath)
        myPath.postValue(dijkstra(dep, arr))
        return myPath
    }

    fun startPath(firstRouteId : Int, lastRouteId : Int, path : List<ParcRoute>){
        if (path.indexOfFirst { it.routeId == firstRouteId.toString() } < path.count() - 1||lastRouteId==firstRouteId){
            Log.e("Bien arrive", "GG")
            return
        }
        bookRoute(routeId = firstRouteId)
        var newPath = path.toMutableList()
        var tmp = path[path.indexOfFirst { it.routeId == firstRouteId.toString() } + 1]
        newPath[newPath.indexOfFirst { it.routeId == firstRouteId.toString() } + 1] = newPath[newPath.indexOfFirst { it.routeId == firstRouteId.toString() }]
        newPath[newPath.indexOfFirst { it.routeId == firstRouteId.toString() }] = tmp

        myPath.postValue(newPath)

        //todo : mettre la nouvelle route en premiere
        Executors.newSingleThreadScheduledExecutor().schedule({
            startPath(
                    path[path.indexOfFirst { it.routeId == firstRouteId.toString() } + 1].routeId.toInt(),
                    lastRouteId,
                    path
            )
        }, 15, TimeUnit.SECONDS)
    }

    fun bookRoute(routeId : Int){
        RouteRepository.getRouteDetail(route = routeId, mobile = 270).observe(context, {
            Log.e("reserve route ${routeId}", "code : ${it.retourRoute[0].code}")
            //timeBeforeChangingRoad = - it.retourRoute[0].delay.toDouble()
            //routeStatus =  RouteStatus.valueOf(it.retourRoute[0].code)
        })
        //Log.e("route reserve :", "tps :${timeBeforeChangingRoad} / status : ${routeStatus.name}")
    }

    fun dijkstra(dep : ParcPoint, arr : ParcPoint) : List<ParcRoute>{
        val pred = emptyMap<ParcPoint, String>().toMutableMap()
        val l = emptyMap<ParcPoint, Double>().toMutableMap()
        val E = emptyList<ParcPoint>().toMutableList()

        listPoint.forEach {
            pred[it] = "0"
            l[it] = 10000.0
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
            val myRoutes = listRoute.filter { it.pointIdDebut == s.id }
            val mySuccesseurs = emptyList<ParcPoint>().toMutableList()
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

        val results = emptyList<ParcRoute>().toMutableList()

        var pointId = arr.id
        while (pointId != dep.id){
            results.add(listRoute.first { route -> route.pointIdFin == pointId && route.pointIdDebut == pred[listPoint.first { it.id == pointId}] })
            pointId = pred[listPoint.first { it.id == pointId}] ?: "0"
        }
        results.reversed().forEach { Log.e("route a prendre", it.routeId) }

        return results.reversed()
    }


}