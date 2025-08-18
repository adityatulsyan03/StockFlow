package com.example.stockflow.data.model

import com.google.gson.annotations.SerializedName

data class SellingUnit(

    @SerializedName("id")
    val id: String?=null,

    @SerializedName("unitName")
    val unitName: String,

)