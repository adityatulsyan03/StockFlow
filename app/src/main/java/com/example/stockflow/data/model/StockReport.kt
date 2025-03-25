package com.example.stockflow.data.model

import com.google.gson.annotations.SerializedName

data class StockReport(

    @SerializedName("id")
    val id: String,

    @SerializedName("item_name")
    val itemName: String,

    @SerializedName("purchased")
    val purchased: String,

    @SerializedName("sold")
    val sold: String,

    @SerializedName("left_stock")
    val leftStock: String,

    @SerializedName("price")
    val price: String
)