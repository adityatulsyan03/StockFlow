package com.example.stockflow.data.model

import com.google.gson.annotations.SerializedName

data class Bank(

    @SerializedName("id")
    val id: String ?= null,

    @SerializedName("accountNumber")
    val bankAccountNumber: String ?= null,

    @SerializedName("ifscCode")
    val ifscCode: String ?= null,

    @SerializedName("holderName")
    val accountHolderName: String ?= null,

    @SerializedName("bankName")
    val bankName: String ?= null,

    @SerializedName("upiId")
    val upiId: String ?= null,

)
