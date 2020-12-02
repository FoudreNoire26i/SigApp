package com.example.sigapp.JsonModelClass

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ParcDistance (
    @SerializedName("distance")
    @Expose
    val retourDistance : List<Distance>
)