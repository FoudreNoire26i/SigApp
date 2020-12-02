package com.example.sigapp.JsonModelClass

import com.example.sigapp.JsonModelClass.Parc
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Racin (
    @SerializedName("PARC")
    @Expose
    val myParc : List<Parc>
)