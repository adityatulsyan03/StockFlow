package com.example.stockflow.data.model

import com.google.gson.annotations.SerializedName

data class Party(

    @SerializedName("id")
    val id: String? = null,

    @SerializedName("isCustomer")
    val customerSupplier: Boolean,

    @SerializedName("name")
    val name: String,

    @SerializedName("phone")
    val phone: String,

    @SerializedName("category")
    val category: String,

    @SerializedName("billingAddress")
    val address: String,

    @SerializedName("deliveryAddress")
    val deliveryAddress: String,

    @SerializedName("postalCode")
    val deliveryPostalCode: Int,

    @SerializedName("gstNumber")
    val gstNumber: String,

    @SerializedName("dob")
    val dob: String,

    @SerializedName("whatsappAlerts")
    val whatsappAlerts: Boolean

)