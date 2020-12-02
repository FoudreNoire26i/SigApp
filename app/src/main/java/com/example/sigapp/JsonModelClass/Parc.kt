package com.example.sigapp.JsonModelClass

import com.example.sigapp.JsonModelClass.ParcPoint
import com.example.sigapp.JsonModelClass.ParcRoute
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Parc (
    @SerializedName("PARC_POINT")
    @Expose
    val listPoints : List<ParcPoint>,

    @SerializedName("PARC_ROUTE")
    @Expose
    val listRoutes : List<ParcRoute>
)
