package com.example.stockflow.data.model

import com.google.gson.annotations.SerializedName

data class User (

    @SerializedName("uid")
    val id: String,

    @SerializedName("photo")
    val profilePhoto: String,

    @SerializedName("industry")
    val industry: String,

    @SerializedName("business_name")
    val businessName: String,

    @SerializedName("contact_name")
    val contactName: String,

    @SerializedName("contact_number")
    val contactNumber: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("gst_number")
    val gstNumber: String,

    @SerializedName("license_number")
    val licenseNumber: String,

    @SerializedName("address")
    val address: String,

    @SerializedName("postal_code")
    val postalCode: Int,

    @SerializedName("signature")
    val signature: String

)