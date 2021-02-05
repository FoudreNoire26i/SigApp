package com.example.sigapp.JsonModelClass

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlin.math.pow
import kotlin.math.sqrt

class ParcRoute (
    @SerializedName("ROU_ID")
    @Expose
    val routeId : String,
    @SerializedName("ROU_POI_ID_DEB")
    @Expose
    val pointIdDebut : String,
    @SerializedName("ROU_POI_ID_FIN")
    @Expose
    val pointIdFin : String,
    @SerializedName("ROU_MOB_ID")
    @Expose
    val mobileId : String,
    @SerializedName("ROU_DISPO")
    @Expose
    val routeDispo: String
)