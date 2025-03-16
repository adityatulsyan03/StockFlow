package com.example.stockflow.data.model

import com.google.gson.annotations.SerializedName
import java.sql.Date
import java.sql.Time

data class Bills(

    @SerializedName("id")
    val id: String,

    @SerializedName("bill_date")
    val billDate: Date,

    @SerializedName("bill_time")
    val billTime: Time,

    @SerializedName("items")
    val items: List<BillItem>,

    @SerializedName("party_id")
    val partyId: String,

    @SerializedName("payment_method")
    val paymentMethod: String,

    @SerializedName("total_amount")
    val totalAmount: Int
)