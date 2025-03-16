package com.example.stockflow.data.model

import com.google.gson.annotations.SerializedName

data class Category(

    @SerializedName("id")
    val id: String,

    @SerializedName("category_name")
    val name: String,

    @SerializedName("type")
    val type: String

)