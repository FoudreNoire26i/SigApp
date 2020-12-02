package com.example.sigapp

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

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
)