package com.example.stockflow.data.model

import com.google.gson.annotations.SerializedName

data class Bank(

    @SerializedName("id")
    val id: String,

    @SerializedName("account_number")
    val bankAccountNumber: String,

    @SerializedName("ifsc_code")
    val ifscCode: String,

    @SerializedName("holder_name")
    val accountHolderName: String,

    @SerializedName("bank_name")
    val bankName: String,

    @SerializedName("upi_id")
    val upiId: String,

)
