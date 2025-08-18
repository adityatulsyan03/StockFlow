package com.example.stockflow.data.model

import com.google.gson.annotations.SerializedName

data class StockReport(

    @SerializedName("id")
    val id: String,

    @SerializedName("inventoryId")
    val inventoryId: String,

    @SerializedName("inventoryName")
    val itemName: String,

    @SerializedName("purchased")
    val purchased: String,

    @SerializedName("sold")
    val sold: String,

    @SerializedName("leftStock")
    val leftStock: String

//    @SerializedName("price")
//    val price: String
)