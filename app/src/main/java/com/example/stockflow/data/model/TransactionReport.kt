package com.example.stockflow.data.model

import com.google.gson.annotations.SerializedName

//sales and purchase report
data class TransactionReport(

    @SerializedName("id")
    val id: String,

    @SerializedName("date")
    val date: String,

    @SerializedName("isInvoice")
    val isSales: Boolean,

    @SerializedName("billId")
    val billNo: String,

    @SerializedName("userId")
    val userName: String,

    @SerializedName("partyId")
    val partyName: String, // Can be Customer or Supplier

    @SerializedName("totalAmount")
    val totalAmount: String,

    @SerializedName("paymentMethod")
    val paymentMethod: String
)