package com.example.stockflow.data.model

import com.google.gson.annotations.SerializedName
import java.sql.Date
import java.sql.Time

data class Bills(

    @SerializedName("id")
    val id: String ?= null,

    @SerializedName("billDate")
    val billDate: String,

    @SerializedName("billTime")
    val billTime: String,

    @SerializedName("items")
    val items: List<BillItem>,

    @SerializedName("partyId")
    val partyId: String,

    @SerializedName("paymentMethod")
    val paymentMethod: String,

    @SerializedName("totalAmount")
    val totalAmount: Float,

    @SerializedName("partyName")
    val partyName: String,

    @SerializedName("isInvoice")
    val isInvoice: Boolean

)