package com.example.stockflow.data.model

import com.google.gson.annotations.SerializedName

data class BillItem(

    @SerializedName("inventoryId")
    val id: String ?= null,

    @SerializedName("name")
    val name: String,

    @SerializedName("quantity")
    val quantity: Int,

    @SerializedName("price")
    val price: Float,

    @SerializedName("totalPrice")
    val totalPrice: Float

)