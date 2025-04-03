package com.example.stockflow.data.model

import com.google.gson.annotations.SerializedName

data class Category(

    @SerializedName("id")
    val id: String ?= null,

    @SerializedName("categoryName")
    val name: String,

    @SerializedName("type")
    val type: String

)