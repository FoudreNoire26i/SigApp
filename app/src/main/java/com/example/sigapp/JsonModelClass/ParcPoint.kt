package com.example.sigapp.JsonModelClass

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlin.math.*

data class ParcPoint (
    @SerializedName("POI_ID")
    @Expose
    val id: String,
    @SerializedName("POI_X")
    @Expose
    val x: String,
    @SerializedName("POI_Y")
    @Expose
    val y: String,
    @SerializedName("POI_Z")
    @Expose
    val z: String,
    @SerializedName("POI_INT")
    @Expose
    val interet: String,
    @SerializedName("POI_NOM")
    @Expose
    val nom : String
){
    val xMetre get() : Double {
        return deg2Lambert(x.toDouble(), y.toDouble()).get(0)
    }
    val yMetre get() : Double {
        return deg2Lambert(x.toDouble(), y.toDouble()).get(1)
    }


    fun deg2Lambert(long : Double, lat: Double):Array<Double> {
        // Constantes Voir NTG_71.pdf
        val n = 0.7289686274;
        val C = 11745793.39;
        val e = 0.08248325676;
        val Xs = 600000.0;
        val Ys =8199695.768;
        var GAMMA0=3600*2+60*20+14.025;
        GAMMA0 = GAMMA0/(180*3600)*Math.PI;
        val lat1 = (lat/(180)*Math.PI)
        val long1= (long/(180)*Math.PI)
// Calcul de L
        val L = 0.5* ln((1.0+ sin(lat1))/(1.0- sin(lat1))) -e/2.0* ln((1.0+e* sin(lat1))/(1.0-e* sin(lat1)));
        val R = C* exp(-n * L);
        val GAMMA =n*(long1-GAMMA0);
// calcul du résultat
        val Lx = Xs+(R* sin(GAMMA));
        val Ly = Ys-(R* cos(GAMMA));
        return arrayOf(Lx, Ly)
    }


    fun distance(pointEnd : ParcPoint): Double {
        return sqrt((pointEnd.xMetre - xMetre).pow(2) + (pointEnd.yMetre - yMetre).pow(2) + (pointEnd.z.toDouble() - z.toDouble()).pow(2))
    }

    fun getPoids(dist : Distance, pointEnd : ParcPoint): Double {
        return (distance(pointEnd)*dist.pente + dist.origine + pointEnd.interet.toDouble())/2
    }
}