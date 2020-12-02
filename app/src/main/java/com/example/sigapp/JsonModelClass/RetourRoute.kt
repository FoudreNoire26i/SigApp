package com.example.sigapp.JsonModelClass

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RetourRoute (
    @SerializedName("retcode")
    @Expose
    val code : String,

    @SerializedName("delai")
    @Expose
    val delay : String
)