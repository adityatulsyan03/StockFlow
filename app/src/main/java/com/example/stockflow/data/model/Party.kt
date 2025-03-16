package com.example.stockflow.data.model

import com.google.gson.annotations.SerializedName

data class Party(

    @SerializedName("id")
    val id: String,

    @SerializedName("is_customer")
    val customerSupplier: Boolean,

    @SerializedName("name")
    val name: String,

    @SerializedName("phone")
    val phone: String,

    @SerializedName("category")
    val category: String,

    @SerializedName("billing_address")
    val address: String,

    @SerializedName("delivery_address")
    val deliveryAddress: String,

    @SerializedName("postal_code")
    val deliveryPostalCode: String,

    @SerializedName("gst_number")
    val gstNumber: String,

    @SerializedName("dob")
    val dob: String

)