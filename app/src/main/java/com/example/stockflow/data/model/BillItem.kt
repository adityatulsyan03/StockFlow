package com.example.stockflow.data.model

import com.google.gson.annotations.SerializedName

data class BillItem(

    @SerializedName("id")
    val id: String ?= null,

    @SerializedName("name")
    val name: String,

    @SerializedName("quantity")
    val quantity: String,

    @SerializedName("price")
    val price: Int,

)