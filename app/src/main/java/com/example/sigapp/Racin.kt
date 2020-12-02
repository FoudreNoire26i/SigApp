package com.example.sigapp

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Racin (
    @SerializedName("PARC")
    @Expose
    val myParc : List<Parc>
)