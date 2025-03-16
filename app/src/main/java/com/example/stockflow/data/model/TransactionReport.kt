package com.example.stockflow.data.model

import com.google.gson.annotations.SerializedName

//sales and purchase report
data class TransactionReport(

    @SerializedName("id")
    val id: String,

    @SerializedName("date")
    val date: String,

    @SerializedName("is_sales")
    val isSales: Boolean,

    @SerializedName("bill_no")
    val billNo: String,

    @SerializedName("party_name")
    val partyName: String, // Can be Customer or Supplier

    @SerializedName("total_amount")
    val totalAmount: String,

    @SerializedName("payment_method")
    val paymentMethod: String
)