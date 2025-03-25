package com.example.stockflow.data.model

import com.google.gson.annotations.SerializedName

data class User (

    @SerializedName("uid")
    val id: String ?= null,

    @SerializedName("photo")
    val profilePhoto: String ?= null,

    @SerializedName("industry")
    val industry: String ?= null,

    @SerializedName("businessName")
    val businessName: String ?= null,

    @SerializedName("contactName")
    val contactName: String ?= null,

    @SerializedName("contactNumber")
    val contactNumber: String ?= null,

    @SerializedName("email")
    val email: String ?= null,

    @SerializedName("gstNumber")
    val gstNumber: String ?= null,

    @SerializedName("licenseNumber")
    val licenseNumber: String ?= null,

    @SerializedName("address")
    val address: String ?= null,

    @SerializedName("postalCode")
    val postalCode: Int ?= 0,

    @SerializedName("signature")
    val signature: String ?= null

)