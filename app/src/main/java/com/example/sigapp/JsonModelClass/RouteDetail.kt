package com.example.sigapp.JsonModelClass

import com.example.sigapp.JsonModelClass.RetourRoute
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RouteDetail (
    @SerializedName("retour")
    @Expose
    val retourRoute : List<RetourRoute>
)