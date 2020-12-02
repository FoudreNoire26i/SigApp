package com.example.sigapp.JsonModelClass

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Distance (
    @SerializedName("pente")
    @Expose
    val pente : Double,

    @SerializedName("origine")
    @Expose
    val origine : Double)